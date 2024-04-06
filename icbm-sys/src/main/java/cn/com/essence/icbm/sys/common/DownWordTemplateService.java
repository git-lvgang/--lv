package cn.com.essence.icbm.sys.common;

import cn.com.essence.wefa.core.bean.CommonRspVo;
import cn.com.essence.wefa.core.bean.ResultCode;
import com.deepoove.poi.XWPFTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class DownWordTemplateService {

    private final Logger log = LoggerFactory.getLogger(DownWordTemplateService.class);
    //word模板名称、绑定数据、word下载到本地的名称、下载word的存放地址、获取word模板的地址
    public CommonRspVo downWordTemplate(String srcFile, Map datas, String destFile, String tempPath,String srcPath){
        log.info("DownWordTemplate.downWordTemplate()...");
        CommonRspVo rsp = new CommonRspVo();
        /*if(!srcPath.endsWith("\\")){
            srcPath = srcPath + "\\";
        }*/
        getTempPath(tempPath);
        XWPFTemplate template = XWPFTemplate.compile(srcPath + srcFile).render(datas);
        FileOutputStream out;
        try {
            out = new FileOutputStream(tempPath + File.separator + destFile);
            template.write(out);
            out.flush();
            out.close();
            rsp.setRtnCode(ResultCode.C_SUCCESS);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            rsp.setRtnCode(ResultCode.C_EXCEPTION);
        } catch (IOException e) {
            e.printStackTrace();
            rsp.setRtnCode(ResultCode.C_EXCEPTION);
        }
        return rsp;
    }

    public void getTempPath(String tempPath) {
        File destFile = new File(tempPath);
        if (!destFile.exists()) {
            destFile.mkdirs();
        }
    }



}
