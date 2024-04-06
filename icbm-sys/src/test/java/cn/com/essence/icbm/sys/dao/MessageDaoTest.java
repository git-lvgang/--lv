package cn.com.essence.icbm.sys.dao;

import cn.com.essence.icbm.sys.bean.po.MessageInfo;
import cn.com.essence.icbm.sys.bean.po.SysMessage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@ActiveProfiles("dao")
@MybatisTest
public class MessageDaoTest {

    @Autowired
    private SysMessageDao messageDao;

    @Test
    public void findPageTest() {
        SysMessage message = messageDao.selectByPrimaryKey(1);
        Assert.assertEquals(message.getMessageId(), new Integer(1));

        messageDao.updateMessageDeleteStatus(Arrays.asList(1, 2), 1, "1");
    }

    @Test
    public void getUserMessageInfo() {
        MessageInfo info = messageDao.getUserMessageInfo(100000);
        System.out.println(info);
    }


    @Test
    public void findUserMessageWithTimestamp() {
        Integer userId = 100000;
        Long timestamp = System.currentTimeMillis();
        String isRead = "1";
        List<SysMessage> messageList = messageDao.findUserMessageWithTimestamp(userId, timestamp, isRead);
        System.out.println(messageList);
    }
}
