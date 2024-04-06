package cn.com.essence.icbm.sys.dao;

import cn.com.essence.icbm.sys.bean.po.SysSearchIdx;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: huangll
 * Date: 2021-1-26
 * Description:
 */
@MybatisTest
@RunWith(SpringRunner.class)
@ActiveProfiles("dao")
public class SysSearchIdxDaoTest {

    @Autowired
    private SysSearchIdxDao dao;

    @Test
    public void selectListTest() {
        List<SysSearchIdx> list = dao.selectList("abc", "001", 10);
        System.out.println(list.size());
    }
}
