package cn.com.essence.icbm.sys.controller;

import cn.com.essence.icbm.sys.bean.vo.FileUploadResultVo;
import cn.com.essence.icbm.sys.service.FileService;
import cn.com.essence.icbm.sys.service.UserInfoService;
import cn.com.essence.icbm.sys.util.BasicExceptionUtil;
import cn.com.essence.wefa.component.log.SysLog;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import cn.com.essence.wefa.core.bean.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: huangll
 * @date: 2021-3-2
 */
@RestController
@Slf4j
public class FileController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private FileService fileService;

    @PostMapping("/file")
    @RequiresUser
    @SysLog(moduleName = "[Icbm_Sys Component 文件上传]",operation = "文件上传")
    public CommonRspVo uploadFile(@RequestParam MultipartFile file) {
        Integer userId = userInfoService.getCurrentUser();
        if (userId == null) {
            return new CommonRspVo(ResultCode.C_NOT_AUTHZ);
        }

        if (file == null || file.isEmpty()) {
            log.error("上传的文件为空，上传失败");
            return new CommonRspVo(ResultCode.C_PARAMS_ERROR);
        }
        log.debug("上传用户ID<{}>, 文件名称<{}>", userId, file.getOriginalFilename());

        try {
            FileUploadResultVo res = fileService.uploadFile(file, userId);
            CommonRspVo rsp = new CommonRspVo(ResultCode.C_SUCCESS);
            rsp.setData(res);
            return rsp;
        } catch (Exception e) {
            log.error("文件上传失败<{}>, msg<{}>", e, e.getMessage());
            return new CommonRspVo(ResultCode.C_EXCEPTION, e.getMessage());
        }
    }

    @PostMapping("/file/batch")
    @RequiresUser
    @SysLog(moduleName = "[Icbm_Sys Component 文件上传]",operation = "文件批量上传")
    public CommonRspVo uploadFiles(@RequestParam MultipartFile[] files) {
        Integer userId = userInfoService.getCurrentUser();
        if (userId == null) {
            return new CommonRspVo(ResultCode.C_NOT_AUTHZ);
        }

        if (files == null || files.length == 0) {
            log.error("上传的文件列表为空，上传失败");
            return new CommonRspVo(ResultCode.C_PARAMS_ERROR);
        }
        log.info("上传用户ID<{}>, 上传文件数量<{}>", userId, files.length);

        List<FileUploadResultVo> list = new ArrayList<>();
        for (int i = 0; i < files.length; i ++) {
            try {
                FileUploadResultVo result = fileService.uploadFile(files[i], userId);
                list.add(result);
            } catch (Exception e) {
                log.error("文件<{}>上传失败,msg<{}>", files[i].getOriginalFilename(), e.getMessage());
                FileUploadResultVo result = new FileUploadResultVo();
                result.setFileName(files[i].getOriginalFilename());
                list.add(result);
            }
        }
        CommonRspVo rsp = new CommonRspVo(ResultCode.C_SUCCESS);
        rsp.setData(list);
        return rsp;
    }

    @GetMapping("/file/{fileId}")
    @RequiresUser
    @SysLog(moduleName = "[Icbm_Sys Component 文件下载]",operation = "下载文件")
    public void downloadFile(HttpServletResponse response, @PathVariable String fileId) {
        fileService.downloadFile(response, fileId);
    }

    @GetMapping("/file/report/{reportIds}")
    @RequiresUser
    @SysLog(moduleName = "[Icbm_Sys Component 文件下载]",operation = "批量下载外部报告附件")
    public void downloadReportFile(HttpServletResponse response,@PathVariable String reportIds){
       List<String> ids= Arrays.asList(reportIds.split(","));
       fileService.downloadReportFile(response,ids);
    }

    @PostMapping("/report/file/size")
    @RequiresUser
    public CommonRspVo queryFileSize(@RequestBody List<String> reportIds){
        return fileService.queryFileSize(reportIds);
    }

    @GetMapping("/file/batch/{fileIds}")
    @RequiresUser
    @SysLog(moduleName = "[Icbm_Sys Component 文件下载]",operation = "批量下载文件")
    public void downloadFilesZip(HttpServletResponse response, @PathVariable String fileIds) {
        String[] arr = fileIds.split(",");
        fileService.downloadFilesZip(response, Arrays.asList(arr));
    }

    @GetMapping("/file/{fileId}/preview")
    @RequiresUser
    public CommonRspVo getFilePreviewUrl(@PathVariable String fileId) {
        CommonRspVo rsp = null;
        try {
            rsp = fileService.getPreviewUrl(fileId);
        } catch (IOException e) {
            throw BasicExceptionUtil.buildRequesetException(e);
        }
        return rsp;
    }

    @DeleteMapping("/file/{fileId}")
    @RequiresUser
    @SysLog(moduleName = "[Icbm_Sys Component 文件上传]",operation = "删除文件")
    public CommonRspVo deleteFile(@PathVariable String fileId) {
        CommonRspVo rsp = fileService.deleteFile(fileId);
        return rsp;
    }



}
