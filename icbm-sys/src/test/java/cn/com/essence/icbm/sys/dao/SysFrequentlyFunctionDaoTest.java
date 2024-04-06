package cn.com.essence.icbm.sys.dao;

import cn.com.essence.icbm.sys.bean.po.SysFrequentlyFunctionSet;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@ActiveProfiles("dao")
@MybatisTest
public class SysFrequentlyFunctionDaoTest {

    @Autowired
    private SysFrequentlyFunctionDao sysFrequentlyFunctionDao;

    @Test
    public void deleteTest() {
        String custCode="1824002";
        int result = sysFrequentlyFunctionDao.deleteCustSet(custCode);
        Assert.assertEquals(result,1);
    }

    @Test
    public void findSysFrequentlyFunctionTest() {
        String custCode="1824002";
        List<SysFrequentlyFunctionSet> SysParam = sysFrequentlyFunctionDao.findSysFreqyentlyMenu(custCode);
        Assert.assertEquals(SysParam.get(0).getCustCode(), new String("1824002"));
    }

    @Test
    public void addSysFrequentlyFunctionSetTest() {

        SysFrequentlyFunctionSet sys = new SysFrequentlyFunctionSet();
        List<SysFrequentlyFunctionSet> sysList =new ArrayList<>();
        sys.setCustCode("1138755");
        sys.setMenuId("1003");
        sys.setMenuName("业务系统菜单");
        sys.setMenuRankNo("3");
        sys.setAddress("中国凤凰大厦9楼");

        sysList.add(sys);
        int result = sysFrequentlyFunctionDao.frequentlyFunction(sysList);
        Assert.assertEquals(result,1);
    }
}
