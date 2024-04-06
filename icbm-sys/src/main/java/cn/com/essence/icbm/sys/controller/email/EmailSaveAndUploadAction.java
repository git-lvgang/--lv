package cn.com.essence.icbm.sys.controller.email;

import cn.com.essence.icbm.sys.bean.po.SysFileInfoExt;
import cn.com.essence.icbm.sys.service.FileService;
import cn.com.essence.wefa.component.log.SysLog;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import cn.com.essence.wefa.core.bean.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@RestController
@Slf4j
public class EmailSaveAndUploadAction {

    @Autowired
    private FileService service;

    @PostMapping("/email/upload")
    @SysLog(moduleName = "[Icbm_Sys Component 文件上传]",operation = "新增文件上传")
    public CommonRspVo uploadFile(@RequestBody SysFileInfoExt fileInfo) {
        try (InputStream inputStream = byte2Input(fileInfo.getBuffer())) {
            service.uploadFile(inputStream, fileInfo);
        } catch (Exception ignore) {
        }
        //在堆空间被垃圾回收掉
        fileInfo = null;
        return new CommonRspVo(ResultCode.C_SUCCESS);
    }

    private static InputStream byte2Input(byte[] buf) {
        return new ByteArrayInputStream(buf);
    }
}
