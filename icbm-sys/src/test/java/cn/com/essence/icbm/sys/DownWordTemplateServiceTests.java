package cn.com.essence.icbm.sys;

import cn.com.essence.icbm.sys.common.DownWordTemplateService;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.ClassUtils;

import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class DownWordTemplateServiceTests {


    /**
     * 真实的service对象
     */
    @InjectMocks
    private DownWordTemplateService downWordTemplateService;

    @Test
    public void testDownWordTemplate() {
        Map map = new HashMap();
        map.put("CUST_NAME","John Doe");
        map.put("ID_CODE","6000102");
        map.put("CONTACT_MODE","18575581095");
        map.put("ADDRESS","中国凤凰大厦1栋9楼");
        map.put("ID_TYPE","机构类型");


        String srcFile = "testTemp.docx";
        String destFile = "测试下载word服务_TEST.docx";
        String tempPath = "D:\\upload\\TEST";
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        String srcPath = path+"/template/";

        CommonRspVo rsp = downWordTemplateService.downWordTemplate(srcFile, map, destFile, tempPath, srcPath);
        Assert.assertEquals(0, rsp.getRtnCode());
    }
}
