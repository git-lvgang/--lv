package cn.com.essence.icbm.sys.service;

import cn.com.essence.icbm.sys.bean.po.SysFileInfo;
import cn.com.essence.icbm.sys.bean.vo.FileUploadResultVo;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author: huangll
 * @date: 2021-3-2
 */
public interface FileService {

    /**
     * 上传文件
     * @param file
     * @param userId
     * @return
     */
    FileUploadResultVo uploadFile(MultipartFile file, Integer userId);

    /**
     * 上传文件
     * @param inputStream
     * @param fileName
     * @param userId
     * @return
     */
    String uploadFile(InputStream inputStream, String fileName, Integer userId);

    /**
     * 上传文件
     * @param inputStream
     * @param fileInfo
     * @return
     */
    String uploadFile(InputStream inputStream, SysFileInfo fileInfo);

    /**
     * 下载文件
     * @param fileId
     * @return
     */
    void downloadFile(HttpServletResponse response, String fileId);

    /**
     * 下载外部报告附件
     * @param reportIds
     * @return
     */
    void downloadReportFile(HttpServletResponse response,List<String> reportIds);

    /**
     * 查询外部报告附件大小
     */
    CommonRspVo queryFileSize(List<String> reportIds);
    /**
     * 下载文件
     * @param fileIds
     * @return
     */
    void downloadFilesZip(HttpServletResponse response, List<String> fileIds);

    /**
     * 删除文件
     * @param fileId
     * @return
     */
    CommonRspVo deleteFile(String fileId);

    /**
     * 获取文件预览url
     * @param fileId
     * @return
     */
    CommonRspVo getPreviewUrl(String fileId) throws IOException;

}
