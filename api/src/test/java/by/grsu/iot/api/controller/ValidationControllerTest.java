package by.grsu.iot.api.controller;

import by.grsu.iot.api.ApiApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApiApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ValidationControllerTest {

    private static final String URL = "/properties";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @WithMockUser("USER")
    @Test
    public void signUp() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andReturn();

        Map<String, Object> map = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Map.class);

        Assert.assertNotNull(map);
        Assert.assertTrue(map.keySet().size() > 0);
    }
}
