package cn.com.essence.icbm.sys;

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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created with IntelliJ IDEA.
 * Author: huangll
 * Date: 2021-2-8
 * Description:
 */
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class SearchControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSearchKeyWord1() throws Exception {
        ResultActions resultAction = mockMvc.perform(MockMvcRequestBuilders.get("/search?keyword=管")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Authorization", "1:test100000")
                .param("uid", "1")
                .param("sign", "test100000"));


        // 设置rsp编码，解决中文乱码
        resultAction.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultAction.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.rtnCode", is(0)))
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.menus", hasSize(1)));
    }

    @Test
    public void testSearchKeyWord2() throws Exception {
        ResultActions resultAction = mockMvc.perform(MockMvcRequestBuilders.get("/search?keyword=测")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Authorization", "1:test100000")
                .param("uid", "1")
                .param("sign", "test100000"));
        resultAction.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultAction.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.rtnCode", is(0)))
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.fund.list", hasSize(1)));
    }


}
