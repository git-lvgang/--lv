package cn.com.essence.icbm.sys.common;

import cn.com.essence.icbm.sys.constant.Constants;
import cn.com.essence.icbm.sys.service.FileService;
import cn.com.essence.wefa.util.FileWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * @author: huangll
 * @date: 2021-3-20
 */
@Component
@Slf4j
public class S3FileWriter implements FileWriter {

    @Autowired
    private FileService fileService;

    @Override
    public String write(InputStream inputStream, String fileName) {
        try {
            String fileId = fileService.uploadFile(inputStream, fileName, Constants.AMDIN_USER_ID);
            return fileId;
        } catch (Exception e) {
            log.error("邮件解析过程中异常，附件" + fileName + "上传到服务报错" + e.getMessage(), e);
        }
        // 上传失败时对应的文件ID
        return "error-upload";
    }
}
