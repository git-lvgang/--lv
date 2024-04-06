package cn.com.essence.icbm.sys;

import cn.com.essence.icbm.sys.bean.vo.NoticeReqVo;
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

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class NoticeControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testNoticeList() throws Exception {
        ResultActions resultAction = mockMvc.perform(MockMvcRequestBuilders.get("/notice/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "1:test100000")
                .param("uid", "1")
                .param("sign", "test100000"));

        resultAction.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultAction.andDo(print()).andExpect(status().isOk())
                    .andExpect(jsonPath("$.rtnCode", is(0)));
    }

    @Test
    public void testAddNotice() throws Exception {
        NoticeReqVo noticeReqVo = new NoticeReqVo();
        noticeReqVo.setNoticeType("1");
        noticeReqVo.setImportance("1");
        noticeReqVo.setNoticeTitle("test");
        noticeReqVo.setNoticeContent("test");
        noticeReqVo.setNoticeSummary("test");
        noticeReqVo.setNoticeSenderId(1);

        ResultActions resultAction = mockMvc.perform(MockMvcRequestBuilders.post("/notice")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "1:test100000")
                .param("uid", "1")
                .param("sign", "test100000")
                .content(JSON.toJSONString(noticeReqVo)));

        resultAction.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultAction.andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testUpdateNoticesReadStatus() throws Exception {
        ResultActions resultAction = mockMvc.perform(MockMvcRequestBuilders.put("/notice/read")
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
        ResultActions resultAction = mockMvc.perform(MockMvcRequestBuilders.put("/notice/top/1/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Authorization", "1:test100000")
                .param("uid", "1")
                .param("sign", "test100000"));

        resultAction.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultAction.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.rtnCode", is(1003)));

    }


}
