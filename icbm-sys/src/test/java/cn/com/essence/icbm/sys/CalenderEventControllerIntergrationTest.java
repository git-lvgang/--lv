package cn.com.essence.icbm.sys;

import cn.com.essence.icbm.sys.bean.vo.CalenderEventReqVo;
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

/**
 * Created with IntelliJ IDEA.
 * Author: huangll
 * Date: 2021-2-6
 * Description:
 */
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class CalenderEventControllerIntergrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testEventtList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/calendar/event/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "1:test100000")
                .param("uid", "1")
                .param("sign", "test100000"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testAddEvent() throws Exception {
        CalenderEventReqVo calenderEventReqVo =  new CalenderEventReqVo();
        calenderEventReqVo.setFundId(1);
        calenderEventReqVo.setEventTitle("test");
        calenderEventReqVo.setEventDetail("test");
        calenderEventReqVo.setEventName("0001");
        calenderEventReqVo.setEventType("1");
        calenderEventReqVo.setEventDate("20210209");

        ResultActions resultAction = mockMvc.perform(MockMvcRequestBuilders.post("/calendar/event")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "1:test100000")
                .param("uid", "1")
                .param("sign", "test100000")
                .contentType(JSON.toJSONString(calenderEventReqVo)));
        resultAction.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultAction.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.rtnCode", is(0)));
    }


}
