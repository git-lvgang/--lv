package cn.com.essence.icbm.sys;
import java.util.Arrays;
import java.util.Date;

import cn.com.essence.icbm.sys.bean.po.SysMessage;
import cn.com.essence.icbm.sys.constant.MessageConstant;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class MessageControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testMessageList() throws Exception {
        ResultActions resultAction = mockMvc.perform(MockMvcRequestBuilders.get("/message/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "1:test100000")
                .param("uid", "1")
                .param("sign", "test100000"));

        resultAction.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultAction.andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testAddMessage() throws Exception {
        SysMessage sysMessage = new SysMessage();
        sysMessage.setImportance("1");
        sysMessage.setMessageSubject("1");
        sysMessage.setMessageContent("123");
        sysMessage.setCreateTime(new Date());
        sysMessage.setIsDeleted(MessageConstant.ISDELETE_N);
        sysMessage.setIsRead(MessageConstant.ISREAD_N);
        sysMessage.setIsTop(MessageConstant.ISTOP_N);
        sysMessage.setUpdateTime(new Date());


        ResultActions resultAction = mockMvc.perform(MockMvcRequestBuilders.post("/message")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "1:test100000")
                .param("uid", "1")
                .param("sign", "test100000")
                .content(JSON.toJSONString(sysMessage)));

        resultAction.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultAction.andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testUpdateMessagesReadStatus() throws Exception {
        ResultActions resultAction = mockMvc.perform(MockMvcRequestBuilders.put("/message/read")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "1:test100000")
                .param("uid", "1")
                .param("sign", "test100000")
                .content(JSON.toJSONString(Arrays.asList(1,2,3))));

        resultAction.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultAction.andDo(print()).andExpect(status().isOk());

    }

    @Test
    public void testUpdateMessageTopStatus() throws Exception {
        ResultActions resultAction = mockMvc.perform(MockMvcRequestBuilders.put("/message/top/1/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Authorization", "1:test100000")
                .param("uid", "1")
                .param("sign", "test100000"));

        resultAction.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultAction.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.rtnCode", is(0)));

    }


}
