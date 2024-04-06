package cn.com.essence.icbm.sys;

import cn.com.essence.icbm.sys.bean.po.SysMessage;
import cn.com.essence.icbm.sys.bean.vo.MessageListReqVo;
import cn.com.essence.icbm.sys.controller.MessageReminderController;
import cn.com.essence.icbm.sys.service.MessageReminderService;
import cn.com.essence.icbm.sys.service.UserInfoService;
import cn.com.essence.wefa.core.bean.CommonListRspVo;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import cn.com.essence.wefa.core.bean.ResultCode;
import cn.com.essence.wefa.core.mybatis.Pager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@RunWith(MockitoJUnitRunner.class)
public class MessageControllerTests {

    @InjectMocks
    MessageReminderController messageReminderController;

    @Mock
    MessageReminderService messageReminderService;

    @Mock
    private UserInfoService userInfoService;

    @Before
    public void setup() {
        // 统一的准备行为,模拟当前用户
        Mockito.when(userInfoService.getCurrentUser()).thenReturn(100000);
    }

    @Test
    public void testgetMessages() {
        // 1. 控制模拟对象行为
        Pager<SysMessage> pager = new Pager<>();
        pager.setResults(new ArrayList<>());
        Mockito.when(messageReminderService.getMessages(any(), anyInt())).thenReturn(pager);

        // 2 调用测试方法
        CommonRspVo rsp = messageReminderController.getMessages(new MessageListReqVo());

        // 对比目标结果跟期望结果
        Assert.assertEquals(ResultCode.C_SUCCESS.getCode(), rsp.getRtnCode());
        Assert.assertTrue(rsp instanceof CommonListRspVo);
        CommonListRspVo listRsp = (CommonListRspVo)rsp;
        Assert.assertEquals(0, listRsp.getTotal());
        Assert.assertEquals(0, listRsp.getData().size());

    }
}
