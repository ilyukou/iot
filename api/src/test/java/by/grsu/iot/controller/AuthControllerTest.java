package by.grsu.iot.controller;

import by.grsu.iot.api.ApiApplication;
import by.grsu.iot.controller.config.PropertiesConfig;
import by.grsu.iot.model.dto.user.ActivateUser;
import by.grsu.iot.model.dto.user.AuthenticationRequest;
import by.grsu.iot.model.dto.user.AuthenticationUser;
import by.grsu.iot.model.dto.user.RegistrationRequest;
import by.grsu.iot.model.sql.Status;
import by.grsu.iot.model.sql.User;
import by.grsu.iot.service.interf.crud.UserCrudService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@Import(PropertiesConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApiApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {

    private static final String URL = "/auth";
    private static final String SIGN_UP_URL = URL + "/signUp";
    private static final String SIGN_IN_URL = URL + "/signIn";
    private static final String ACTIVATE_URL = URL + "/activate";
    private static final String RESTORE_URL = URL + "/restore";
    private static final String EMAIL_SEND_CODE_URL = URL + "/email/sendCode";
    private final String email = "email";
    private final String username = "username";
    private final String password = "password";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserCrudService userCrudService;

    public MvcResult send(Object request, String url) throws Exception {
        return mockMvc.perform(post(url).with(anonymous())
                .content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void signIn() throws Exception {
        send(new RegistrationRequest(username, password, email), SIGN_UP_URL);
        MvcResult mvcResult = send(new AuthenticationRequest(username, password), SIGN_IN_URL);

        AuthenticationUser user = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AuthenticationUser.class);

        Assert.assertEquals(username, user.getUsername());
        Assert.assertNotNull(user.getToken());
        Assert.assertNotNull(user.getTokenValidity());
    }

    @Test
    public void signUp() throws Exception {
        send(new RegistrationRequest(username, password, email), SIGN_UP_URL);
    }

    @Test
    public void activate() throws Exception {
        send(new RegistrationRequest(username, password, email), SIGN_UP_URL);
        User user = userCrudService.getByUsername(username);

        if (userCrudService.getDefaultUserStatus().equals(Status.ACTIVE)) {
            Assert.assertEquals(Status.ACTIVE, user.getStatus());

        } else if (userCrudService.getDefaultUserStatus().equals(Status.NOT_ACTIVE)) {
            Assert.assertEquals(Status.NOT_ACTIVE, user.getStatus());

            send(new ActivateUser(username, user.getEmail().getCode()), ACTIVATE_URL);
            Assert.assertEquals(Status.ACTIVE, userCrudService.getByUsername(username).getStatus());

        } else {
            // ignore
        }
    }
}
