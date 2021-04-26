package by.grsu.iot.controller.crud;

import by.grsu.iot.api.ApiApplication;
import by.grsu.iot.api.controller.crud.ProjectCrudController;
import by.grsu.iot.controller.config.PropertiesConfig;
import by.grsu.iot.model.dto.project.ProjectDto;
import by.grsu.iot.model.dto.project.ProjectForm;
import by.grsu.iot.model.dto.project.ProjectFormUpdate;
import by.grsu.iot.model.dto.user.RegistrationRequest;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.Status;
import by.grsu.iot.model.sql.User;
import by.grsu.iot.producer.interf.EmailCodeProducer;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.repository.interf.UserRepository;
import by.grsu.iot.service.interf.crud.ProjectCrudService;
import by.grsu.iot.service.interf.crud.UserCrudService;
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
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@Import(PropertiesConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApiApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectCrudControllerTest {

    private static final String CONTROLLER_URL = ProjectCrudController.class.getAnnotation(RequestMapping.class).value()[0];

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email@emial.com";

    private static final String PROJECT_NAME = "Project Name";
    private static final String TITLE_NAME = "Title name";

    private static final String PROJECT_NAME_2 = "Project Name2";
    private static final String TITLE_NAME_2 = "Title name2";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProjectCrudService projectCrudService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserCrudService userCrudService;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private EmailCodeProducer producer;

    private MvcResult send(String url, String method, Object body, RequestPostProcessor credential) throws Exception {
        MockHttpServletRequestBuilder builder;

        switch (method) {
            case "get":
                builder = get(url).with(credential);
                break;

            case "put":
                builder = put(url).with(credential);
                break;

            case "post":
                builder = post(url).with(credential);
                break;

            case "delete":
                builder = delete(url).with(credential);
                break;
            default:
                throw new IllegalArgumentException();
        }


        return mockMvc.perform(builder
                .content(objectMapper.writeValueAsString(body)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    private void activate(String username) {
        User u = userCrudService.getByUsername(username);
        u.setStatus(Status.ACTIVE);
        userRepository.update(u);
    }

    @Test
    public void create() throws Exception {
        User user = userCrudService.create(new RegistrationRequest(USERNAME, PASSWORD, EMAIL));
        activate(USERNAME);

        send(CONTROLLER_URL, "post", new ProjectForm(PROJECT_NAME, TITLE_NAME),
                SecurityMockMvcRequestPostProcessors.user(USERNAME));

        Set<Project> projectList = projectRepository.getUserProjectsByUser(user);

        Assert.assertEquals(1, projectList.size());

        Project project = projectList.iterator().next();

        Assert.assertEquals(PROJECT_NAME, project.getName());
        Assert.assertEquals(TITLE_NAME, project.getTitle());
        Assert.assertEquals(USERNAME, projectRepository.getProjectOwnerUsername(project.getId()));
    }

    @Test
    public void get_() throws Exception {
        User user = userCrudService.create(new RegistrationRequest(USERNAME, PASSWORD, EMAIL));
        activate(USERNAME);
        Project p = projectCrudService.create(new ProjectForm(PROJECT_NAME, TITLE_NAME), USERNAME);

        MvcResult result = send(CONTROLLER_URL + "/" + p.getId(), "get", null, SecurityMockMvcRequestPostProcessors.user(USERNAME));

        ProjectDto dto = objectMapper.readValue(result.getResponse().getContentAsString(), ProjectDto.class);

        Assert.assertEquals(PROJECT_NAME, dto.getName());
        Assert.assertEquals(TITLE_NAME, dto.getTitle());

        Assert.assertEquals(p.getId(), dto.getId());
    }

    @Test
    public void update_whenUpdateName() throws Exception {
        User user = userCrudService.create(new RegistrationRequest(USERNAME, PASSWORD, EMAIL));
        activate(USERNAME);
        Project p = projectCrudService.create(new ProjectForm(PROJECT_NAME, TITLE_NAME), USERNAME);

        ProjectFormUpdate form = new ProjectFormUpdate();
        form.setName(PROJECT_NAME_2);
        send(CONTROLLER_URL + "/" + p.getId(), "put", form, SecurityMockMvcRequestPostProcessors.user(USERNAME));

        Set<Project> projectList = projectRepository.getUserProjectsByUser(user);

        Assert.assertEquals(1, projectList.size());

        Project project = projectList.iterator().next();

        Assert.assertEquals(PROJECT_NAME_2, project.getName());
        Assert.assertEquals(TITLE_NAME, project.getTitle());
        Assert.assertEquals(USERNAME, projectRepository.getProjectOwnerUsername(project.getId()));
    }

    @Test
    public void update_whenUpdateTitle() throws Exception {
        User user = userCrudService.create(new RegistrationRequest(USERNAME, PASSWORD, EMAIL));
        activate(USERNAME);
        Project p = projectCrudService.create(new ProjectForm(PROJECT_NAME, TITLE_NAME), USERNAME);

        ProjectFormUpdate form = new ProjectFormUpdate();
        form.setTitle(TITLE_NAME_2);
        send(CONTROLLER_URL + "/" + p.getId(), "put", form, SecurityMockMvcRequestPostProcessors.user(USERNAME));

        Set<Project> projectList = projectRepository.getUserProjectsByUser(user);

        Assert.assertEquals(1, projectList.size());

        Project project = projectList.iterator().next();

        Assert.assertEquals(PROJECT_NAME, project.getName());
        Assert.assertEquals(TITLE_NAME_2, project.getTitle());
        Assert.assertEquals(USERNAME, projectRepository.getProjectOwnerUsername(project.getId()));
    }

    @Test
    public void delete_() throws Exception {
        User user = userCrudService.create(new RegistrationRequest(USERNAME, PASSWORD, EMAIL));
        activate(USERNAME);
        Project p = projectCrudService.create(new ProjectForm(PROJECT_NAME, TITLE_NAME), USERNAME);

        Assert.assertTrue(projectRepository.isExist(p.getId()));

        MvcResult result = send(CONTROLLER_URL + "/" + p.getId(), "delete", null, SecurityMockMvcRequestPostProcessors.user(USERNAME));

        Assert.assertFalse(projectRepository.isExist(p.getId()));
    }
}
