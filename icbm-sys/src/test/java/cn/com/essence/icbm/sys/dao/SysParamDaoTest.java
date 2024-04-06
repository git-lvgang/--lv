package cn.com.essence.icbm.sys.dao;

import cn.com.essence.icbm.sys.bean.po.SysParam;
import cn.com.essence.icbm.sys.bean.po.SysParamItems;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@ActiveProfiles("dao")
@MybatisTest
public class SysParamDaoTest {

    @Autowired
    private SysParamDao sysParamDao;

    @Test
    public void findSysParamPageTest() {
        SysParam sysParam = new SysParam();
        sysParam.setParamCode("TEST_CODE");
        sysParam.setMaintainFlag('1');
        sysParam.setParamName("测试");
        List<SysParam> sysParamList = sysParamDao.findSysParamPage(sysParam);
        Assert.assertEquals(sysParamList.get(0).getParamCode(), new String("TEST_CODE"));
    }

    @Test
    public void addSysParamTest() {

        SysParam sysParam = new SysParam();
        sysParam.setParamCode("CESHI_CODE");
        sysParam.setParamName("测试2");
        sysParam.setMaintainFlag('1');
        sysParam.setRemark("测试12345");
        int result = sysParamDao.addSysParam(sysParam);
        Assert.assertEquals(result,1);
    }

    @Test
    public void updateSysParamTest() {
        SysParam sysParam = new SysParam();
        sysParam.setParamCode("TEST_CODE");
        sysParam.setParamName("测试2");
        int result = sysParamDao.updateSysParam(sysParam);
        Assert.assertEquals(result,1);
    }

    @Test
    public void deleteSysParamTest() {
        int result = sysParamDao.deleteSysParam("TEST_CODE");
        Assert.assertEquals(result,1);
    }

    @Test
    public void findSysParamItemsTest() {
        String code = "TEST_CODE";
        List<SysParamItems> sysParamItems = sysParamDao.findSysParamItems(code);
        Assert.assertEquals(sysParamItems.get(0).getParamCode(), "TEST_CODE");
    }

    @Test
    public void deleteSysParamItemsTest() {
        int result = sysParamDao.deleteSysParamItems("1");
        Assert.assertEquals(result,1);
    }

    @Test
    public void addSysParamItemsTest() {
        SysParamItems sysParamItems = new SysParamItems();
        sysParamItems.setParamCode("CESHI_CODE");
        sysParamItems.setParamItem('2');
        sysParamItems.setParamItemName("测试用数据");
        int result = sysParamDao.addSysParamItems(sysParamItems);
        Assert.assertEquals(result,1);
    }

    @Test
    public void updateSysParamItemsTest() {
        SysParamItems sysParamItems = new SysParamItems();
        sysParamItems.setParamItem('1');
        sysParamItems.setParamCode("TEST_CODE");
        int result = sysParamDao.updateSysParamItems(sysParamItems);
        Assert.assertEquals(result,1);
    }
}
