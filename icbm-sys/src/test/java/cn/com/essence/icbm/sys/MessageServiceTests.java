package cn.com.essence.icbm.sys;

import cn.com.essence.icbm.sys.bean.po.MessageInfo;
import cn.com.essence.icbm.sys.bean.po.SysMessage;
import cn.com.essence.icbm.sys.bean.vo.MessageListReqVo;
import cn.com.essence.icbm.sys.dao.SysMessageDao;
import cn.com.essence.icbm.sys.service.impl.MessageReminderServiceImpl;
import cn.com.essence.wefa.core.mybatis.Pager;
import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class MessageServiceTests {

    /**
     * mock对象，用于替换service里面使用的dao对象
     */
    @Mock
    private SysMessageDao messageDao;

    /**
     * 真实的service对象
     */
    @InjectMocks
    private MessageReminderServiceImpl service;

    @Before
    public void setup() {
        // 设置好mock行为
        Mockito.when(messageDao.findPage(Mockito.any())).thenReturn(new ArrayList<>());

        MessageInfo info =  new MessageInfo();
        Mockito.when(messageDao.getUserMessageInfo(Mockito.anyInt())).thenReturn(info);
    }

    @Test
    public void testGetMessages() {

        Pager<SysMessage> res = service.getMessages(new MessageListReqVo(), 100000);
        Assert.assertEquals(0, res.getTotal());
    }

    @Test
    public void testJson() {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, String>> menuList = new ArrayList<>();
        map.put("menu", menuList);
        Map<String, String> menuMap = new HashMap<>();
        menuMap.put("menuName", "产品列表");
        menuMap.put("path", "/ambs/fund/list");
        menuList.add(menuMap);


        List<Map<String, String>> fundList = new ArrayList<>();
        map.put("fund", fundList);
        Map<String, String> fundMap = new HashMap<>();
        fundMap.put("fundId", "110");
        fundMap.put("fundCode", "S012312");
        fundMap.put("fundName", "安鑫一号");
        fundList.add(fundMap);
        List<Map<String, String>> orgList = new ArrayList<>();
        map.put("organ", orgList);

        System.out.println(JSON.toJSONString(map));


    }

    @Test
    public void getUserMessageInfo() {
        MessageInfo info = service.getUserMessageInfo(100000, null);
        Assert.assertNull(info.getLastMessageList());

        info = service.getUserMessageInfo(100000, 0L);
        Assert.assertNull(info.getLastMessageList());
    }
}
