package by.grsu.iot.api.controller.crud;

import by.grsu.iot.api.ApiApplication;
import by.grsu.iot.api.config.RepositoryTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

@Transactional
@Import(RepositoryTestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApiApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeviceCrudControllerTest {

    private static final String CONTROLLER_URL = DeviceCrudController.class.getAnnotation(RequestMapping.class).value()[0];

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email@emial.com";

    private static final String PROJECT_NAME = "Project Name";
    private static final String TITLE_NAME = "Title name";

    private static final String DEVICE_NAME = "Device Name";
    private static final String DEVICE_NAME_2 = "Device Name2";

    @Test
    public void create() {

    }
}
