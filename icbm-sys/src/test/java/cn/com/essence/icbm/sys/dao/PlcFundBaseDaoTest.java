package cn.com.essence.icbm.sys.dao;

import cn.com.essence.icbm.sys.bean.po.PlcFundBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
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
public class PlcFundBaseDaoTest {

    @Autowired
    private PlcFundBaseDao dao;

    @Test
    public void fundFundsTest() {
        List<PlcFundBase> list = dao.findFunds(Arrays.asList(1, 2, 3));
        System.out.println(list.size());

        list = dao.findFullNameNotEmptyFunds(0 ,10);
        System.out.println(list.size());

        list = dao.findSaleCodeNotEmptyFunds(0 ,10);
        System.out.println(list.size());
    }
}
