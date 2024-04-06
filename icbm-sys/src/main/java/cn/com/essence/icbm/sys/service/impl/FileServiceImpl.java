package cn.com.essence.icbm.sys.service.impl;

import cn.com.essence.icbm.sys.bean.po.SysFileInfo;
import cn.com.essence.icbm.sys.bean.vo.FileUploadResultVo;
import cn.com.essence.icbm.sys.common.FileClientFactory;
import cn.com.essence.icbm.sys.constant.BaseConstant;
import cn.com.essence.icbm.sys.dao.SysFileInfoDao;
import cn.com.essence.icbm.sys.service.FileService;
import cn.com.essence.icbm.sys.util.BasicExceptionUtil;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import cn.com.essence.wefa.core.bean.ResultCode;
import cn.com.essence.wefa.store.s3.StoreManager;
import cn.com.essence.wefa.util.IDGenerator;
import cn.com.essence.wefa.util.PreviewClient;
import cn.com.essence.wefa.util.PreviewResult;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
/**
 * @author: huangll
 * @date: 2021-3-2
 */

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    private static final String PREVIEW_URL = "previewUrl";

    @Value("${max.fileSize:10}")
    private long maxFileSize;

    @Autowired
    private SysFileInfoDao fileInfoDao;

    @Autowired
    private PreviewClient previewClient;

    @Autowired
    private FileClientFactory clientFactory;

    /**
     * 文件上传
     * @param file
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public FileUploadResultVo uploadFile(MultipartFile file, Integer userId){
        String fileName = file.getOriginalFilename();
        try {
            String fileId = uploadFile(file.getInputStream(), fileName, userId);
            FileUploadResultVo result = new FileUploadResultVo();
            result.setFileName(fileName);
            result.setFileId(fileId);
            result.setUploadTime(new Date());
            long fileSize = file.getInputStream().available();
            result.setFileSize(fileSize);
            return result;
        } catch (IOException e) {
            log.error("文件上传异常:"+e.getMessage(), e);
            throw BasicExceptionUtil.buildRequesetException(e);
        }
    }

    /**
     *
     * @param inputStream
     * @param fileName
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public String uploadFile(InputStream inputStream, String fileName, Integer userId) {
        String fileId = IDGenerator.generateID();
        log.info("upload file fileId<{}> fileName<{}>", fileId, fileName);

        Date date = new Date();
        SysFileInfo fileInfo = new SysFileInfo();
        fileInfo.setFileId(fileId);
        fileInfo.setFileName(fileName);
        fileInfo.setUserId(userId);
        fileInfo.setCreateTime(date);
        fileInfo.setUpdateTime(date);
        fileInfo.setIsDelete(BaseConstant.ISDELETE_N);
        int res = fileInfoDao.insert(fileInfo);
        if (res == 0) {
            // 理论上很难出现(随机出两个相同的32位字符中)，出现让用户重试就好了
            log.error("处理异常，msg:随机生成的文件ID(32位)重复");
            throw BasicExceptionUtil.buildRequesetException();
        }

        StoreManager client = clientFactory.getClient();
        try {
            client.uploadStream(fileId, inputStream, true);
        } catch (Exception e) {
            log.error("文件上传异常:"+ fileName + e.getMessage(), e);
            throw BasicExceptionUtil.buildRequesetException(e);
        } finally {
            client.shutdown();
        }

        return fileId;
    }

    @Override
    @Transactional
    public String uploadFile(InputStream inputStream, SysFileInfo fileInfo) {

        fileInfo.setIsDelete(BaseConstant.ISDELETE_N);
        int res = fileInfoDao.insert(fileInfo);
        if (res == 0) {
            // 理论上很难出现(随机出两个相同的32位字符中)，出现让用户重试就好了
            log.error("处理异常，msg:随机生成的文件ID(32位)重复");
            throw BasicExceptionUtil.buildRequesetException();
        }

        StoreManager client = clientFactory.getClient();
        try {
            client.uploadStream(fileInfo.getFileId(), inputStream, true);
        } catch (Exception e) {
            log.error("文件上传异常:"+ fileInfo.getFileName() + e.getMessage(), e);
            throw BasicExceptionUtil.buildRequesetException(e);
        } finally {
            client.shutdown();
        }

        return fileInfo.getFileId();
    }

    /**
     * 文件下载接口
     * @param fileId
     * @return
     */
    @Override
    @Transactional
    public void downloadFile(HttpServletResponse response, String fileId) {
        SysFileInfo fileInfo = fileInfoDao.selectByPrimaryKey(fileId);
        if (fileInfo == null || Objects.equals(BaseConstant.ISDELETE_Y, fileInfo.getIsDelete())) {
            log.error("下载失败，文件<{}>不存在", fileId);
            throw BasicExceptionUtil.buildDataNotFound("文件不存在");
            //return new CommonRspVo(ResultCode.C_DATA_NOT_FOUND);
        }
        StoreManager client = clientFactory.getClient();
        sethttpResponse(response, fileInfo.getFileName());

        try {
            //BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            BufferedInputStream bis = new BufferedInputStream(client.downStream(fileId));
            byte[] buff = new byte[1024];
            OutputStream os  = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
            bis.close();
            os.close();
        } catch (IOException e) {
            log.error("文件下载失败{}, msg<{}>",e, e.getMessage());
        } finally {
            client.shutdown();
        }
//        try {
//            S3ObjectInputStream inputStream = client.downStream(fileId);
//            //boolean file = client.downloadFileAppoint(fileId, "./", fileInfo.getFileName());
//            //client.downloadIn
//        } catch (IOException e) {
//            log.error("exception <{}> msg<{}>", e, e.getMessage());
//            throw e;
//        } finally {
//            // TODO 关闭连接
//        }

    }

    @Override
    public void downloadReportFile(HttpServletResponse response, List<String> reportIds) {
        List<String> fileIds=selectFileId(reportIds);
        downloadFilesZip(response,fileIds);
    }

    @Override
    public CommonRspVo queryFileSize(List<String> reportIds) {
        CommonRspVo rsp = new CommonRspVo();
        List<SysFileInfo> fileInfos = fileInfoDao.selectFiles(selectFileId(reportIds));
        StoreManager client = clientFactory.getClient();
        long fileSize=0;
        try{
            for (SysFileInfo file : fileInfos) {
                BufferedInputStream bis = new BufferedInputStream(client.downStream(file.getFileId()));
                fileSize=fileSize+bis.available();
                bis.close();
            }
        }catch (IOException e){
        }
        client.shutdown();
        if(fileSize>=(maxFileSize*1024*1024)){
            rsp.setRtnCode(ResultCode.C_SUCCESS);
            rsp.setData(true);
            return rsp;
        }
        rsp.setRtnCode(ResultCode.C_SUCCESS);
        rsp.setData(false);
        return rsp;
    }

    //通过外部报告reportId获取file文件id
    private List<String> selectFileId(List<String> reportIds){
        List<JSONObject> jsons= fileInfoDao.selectFileList(reportIds);
        List<String> fileIds= Lists.newArrayList();
        for (JSONObject jsonObject: jsons) {
            if(jsonObject!=null){
                JSONArray file_list = jsonObject.getJSONArray("FILE_LIST");
                for (Object object : file_list) {
                    if (Objects.nonNull(object) && object instanceof JSONObject){
                        JSONObject jsonObject1=(JSONObject)object;
                        fileIds.add(jsonObject1.getString("fileId"));
                    }
                }
            }
        }
        return fileIds;
    }

    @Override
    @Transactional
    public void downloadFilesZip(HttpServletResponse response, List<String> fileIds) {
        List<SysFileInfo> fileInfos = fileInfoDao.selectFiles(fileIds);
        log.info("download files size<{}>", fileInfos.size());
        StoreManager client = clientFactory.getClient();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = sdf.format(date) + ".zip";
        sethttpResponse(response, fileName);
        //设置压缩流：直接写入response，实现边压缩边下载
        ZipOutputStream zipos = null;
        try {
            zipos = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
            zipos.setMethod(ZipOutputStream.DEFLATED); //设置压缩方法
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        //循环将文件写入压缩流
        DataOutputStream os = null;
        Map<String, Integer> map = new HashMap<>();
        for (SysFileInfo file : fileInfos) {
            try {
                BufferedInputStream bis = new BufferedInputStream(client.downStream(file.getFileId()));
                String name = file.getFileName();
                if (map.containsKey(name)) {
                    Integer idx = map.get(name);
                    String[] list = name.split("\\.");
                    list[0] = list[0] + "(" + idx + ")";
                    map.put(name, idx+1);
                    name = StringUtils.join(list, ".");
                } else {
                    map.put(name, 1);
                }
                zipos.putNextEntry(new ZipEntry(name));
                os = new DataOutputStream(zipos);
                byte[] buff = new byte[1024];
                int i = 0;
                while ((i = bis.read(buff)) != -1) {
                    os.write(buff, 0, i);
                }
                bis.close();
                zipos.closeEntry();
            } catch (IOException e) {
                log.error("文件下载失败{}, msg<{}>",e, e.getMessage());
            }
        }
        //关闭流
        try {
            os.flush();
            os.close();
            zipos.close();
            client.shutdown();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void sethttpResponse(HttpServletResponse response, String fileName) {
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        //response.setContentLength((int) file.length());
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8") );
        } catch (UnsupportedEncodingException e) {
            log.error("response 设置header报错， 文件名<{}>编码异常", fileName);
        }
    }

    /**
     * 文件删除接口
     * @param fileId
     * @return
     */
    @Override
    @Transactional
    public CommonRspVo deleteFile(String fileId) {
        SysFileInfo fileInfo = fileInfoDao.selectByPrimaryKey(fileId);
        // TODO 校验权限, 是不是要求自发自删除
        if (fileInfo == null || Objects.equals(BaseConstant.ISDELETE_Y, fileInfo.getIsDelete())) {
            log.error("删除文件失败，文件<{}>不存在", fileId);
            return new CommonRspVo(ResultCode.C_DATA_NOT_FOUND);
        }

        fileInfo = new SysFileInfo();
        fileInfo.setFileId(fileId);
        fileInfo.setUpdateTime(new Date());
        fileInfo.setIsDelete(BaseConstant.ISDELETE_Y);
        int fileIn=fileInfoDao.updateByPrimaryKeySelective(fileInfo);
        log.info("文件删除成功，<{}>",fileIn);
        return new CommonRspVo(ResultCode.C_SUCCESS);
    }

    /**
     * 获取预览路径
     * @param fileId
     * @return
     */
    @Override
    public CommonRspVo getPreviewUrl(String fileId) throws IOException {
        SysFileInfo fileInfo = fileInfoDao.selectByPrimaryKey(fileId);
        if (fileInfo == null || Objects.equals(BaseConstant.ISDELETE_Y, fileInfo.getIsDelete())) {
            log.error("预览失败，文件<{}>不存在", fileId);
            return new CommonRspVo(ResultCode.C_DATA_NOT_FOUND);
        }

        String previewUrl = null;
        if (!StringUtils.isEmpty(fileInfo.getPreviewFileId())) {
            // 不为空,说明曾预览过,直接通过预览服务文件ID
            try {
                previewUrl = previewClient.getPreviewPath(fileInfo.getFileName(), fileInfo.getPreviewFileId());
            } catch (Exception e) {
                log.error("通过预览服务返回的文件ID获取预览路径失败，msg<{}>", e.getMessage());
            }
        }
        if (previewUrl == null) {
            // 通过预览服务的文件ID拿不到预览路径，则重新上传文件到预览服务
            StoreManager client = null;
            S3ObjectInputStream inputStream = null;
            try {
                client = clientFactory.getClient();
                inputStream = client.downStream(fileId);
                PreviewResult result = previewClient.getPreviewPath(inputStream, fileInfo.getFileName());
                fileInfo.setPreviewFileId(result.getFileId());
                previewUrl = result.getPreviewPath();
                // 更新预览服务文件ID
                SysFileInfo tmp = new SysFileInfo();
                tmp.setFileId(fileId);
                tmp.setPreviewFileId(result.getFileId());
                fileInfoDao.updateByPrimaryKeySelective(tmp);
            } catch (Exception e) {
                log.error("获取预览路径失败，msg<{}>", e.getMessage());
                return new CommonRspVo(ResultCode.C_EXCEPTION, e.getMessage());
            } finally {
                if (client != null) {
                    client.shutdown();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        }

        Map<String, String> map = new HashMap<>();
        map.put(PREVIEW_URL, previewUrl);
        CommonRspVo rsp = new CommonRspVo(ResultCode.C_SUCCESS);
        rsp.setData(map);
        return rsp;
    }

}
