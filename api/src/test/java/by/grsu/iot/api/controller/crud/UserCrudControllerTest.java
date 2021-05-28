package by.grsu.iot.api.controller.crud;

import by.grsu.iot.api.ApiApplication;
import by.grsu.iot.api.config.RepositoryTestConfig;
import by.grsu.iot.api.model.dto.user.AuthenticationRequest;
import by.grsu.iot.api.model.dto.user.AuthenticationUser;
import by.grsu.iot.api.model.dto.user.RegistrationRequest;
import by.grsu.iot.api.model.dto.user.UserUpdateForm;
import by.grsu.iot.api.model.exception.EntityNotFoundApplicationException;
import by.grsu.iot.api.model.sql.Status;
import by.grsu.iot.api.model.sql.User;
import by.grsu.iot.api.repository.sql.user.UserRepository;
import by.grsu.iot.api.service.crud.user.EmailCrudService;
import by.grsu.iot.api.service.crud.user.UserCrudService;
import by.grsu.iot.api.service.producer.EmailCodeProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@Import(RepositoryTestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApiApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserCrudControllerTest {

    private static final String UserCrudController_URL = UserCrudController.class.getAnnotation(RequestMapping.class).value()[0];

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email@emial.com";

    private static final String USERNAME_2 = "username2";
    private static final String PASSWORD_2 = "password2";
    private static final String EMAIL_2 = "email2@emial.com";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserCrudService userCrudService;

    @Autowired
    private EmailCrudService emailCrudService;

    @MockBean
    private EmailCodeProducer producer;

    @Autowired
    private UserRepository userRepository;

    private MvcResult send(String url, Object body, RequestPostProcessor credential) throws Exception {
        return mockMvc.perform(put(url).with(credential)
                .content(objectMapper.writeValueAsString(body)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    private void activate(String username) {
        User u = userCrudService.getByUsername(username);
        u.setStatus(Status.ACTIVE);
        userRepository.update(u);
    }

    @WithMockUser("username")
    @Test
    public void update_whenUpdateUsername() throws Exception {
        userCrudService.create(new RegistrationRequest(USERNAME, PASSWORD, EMAIL));
        activate(USERNAME);

        UserUpdateForm form = new UserUpdateForm();
        form.setUsername(USERNAME_2);

        send(UserCrudController_URL, form, SecurityMockMvcRequestPostProcessors.user(USERNAME));
        activate(USERNAME_2);

        Assert.assertThrows(EntityNotFoundApplicationException.class, () -> userCrudService.getByUsername(USERNAME));

        User user = userCrudService.getByUsername(USERNAME_2);
        Assert.assertEquals(USERNAME_2, user.getUsername());
    }

    @WithMockUser("username")
    @Test
    public void update_whenUpdateEmail() throws Exception {
        userCrudService.create(new RegistrationRequest(USERNAME, PASSWORD, EMAIL));
        activate(USERNAME);

        UserUpdateForm form = new UserUpdateForm();
        form.setEmail(EMAIL_2);

        send(UserCrudController_URL, form, SecurityMockMvcRequestPostProcessors.user(USERNAME));

        Assert.assertNull(emailCrudService.getByAddress(EMAIL));

        User user = userCrudService.getByUsername(USERNAME);
        Assert.assertEquals(EMAIL_2, user.getEmail().getAddress());
    }

    @WithMockUser("username")
    @Test
    public void update_whenUpdatePassword() throws Exception {
        userCrudService.create(new RegistrationRequest(USERNAME, PASSWORD, EMAIL));
        activate(USERNAME);

        UserUpdateForm form = new UserUpdateForm();
        form.setPassword(PASSWORD_2);

        send(UserCrudController_URL, form, SecurityMockMvcRequestPostProcessors.user(USERNAME));
        activate(USERNAME);

        try {
            userCrudService.authenticate(new AuthenticationRequest(USERNAME, PASSWORD));

            // expected exception. Bad credential
            fail();
        } catch (Exception e) {
            // ignore
        }


        AuthenticationUser user = new AuthenticationUser();
        try {
            user = userCrudService.authenticate(new AuthenticationRequest(USERNAME, PASSWORD_2));


        } catch (Exception e) {
            // not expected exception. Bad credential
            fail();
        }

        Assert.assertEquals(USERNAME, user.getUsername());
        Assert.assertNotNull(user.getToken());
        Assert.assertNotNull(user.getTokenValidity());
    }
}
