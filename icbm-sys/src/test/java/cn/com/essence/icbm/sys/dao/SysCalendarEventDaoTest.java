package cn.com.essence.icbm.sys.dao;

import cn.com.essence.icbm.sys.bean.po.SysCalendarEvent;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@ActiveProfiles("dao")
@MybatisTest
public class SysCalendarEventDaoTest {

    @Autowired
    private SysCalendarEventDao eventDao;

    @Test
    public void selectEventTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("fundId", "");
        List<SysCalendarEvent> list = eventDao.selectEvents(map);
        Assert.assertEquals(1, list.size());

        map.put("fundId", "2");
        list = eventDao.selectEvents(map);
        Assert.assertEquals(0, list.size());

        map.remove("fundId");
        list = eventDao.selectEvents(map);
        Assert.assertEquals(1, list.size());

        map.put("monthDate", "202101");
        list = eventDao.selectEvents(map);
        Assert.assertEquals(1, list.size());

        map.put("monthDate", "202102");
        list = eventDao.selectEvents(map);
        Assert.assertEquals(0, list.size());


    }
}
