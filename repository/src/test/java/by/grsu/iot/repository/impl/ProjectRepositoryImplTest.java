package by.grsu.iot.repository.impl;

import by.grsu.iot.model.sql.Device;
import by.grsu.iot.model.sql.Email;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.User;
import by.grsu.iot.repository.config.RepositoryTestConfig;
import by.grsu.iot.repository.interf.DeviceRepository;
import by.grsu.iot.repository.interf.EmailRepository;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.repository.interf.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RepositoryTestConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectRepositoryImplTest {

    private final String username = "username";
    private final String password = "password";
    private final String address = "address";
    private final String name = "projectName";
    private final String title = "Title";
    private final String second_name = "secondProjectName";
    private final String second_title = "secondTitle";
    private final String device_state = "off";
    private final List<String> device_states = Arrays.asList(device_state, "on");

    @MockBean
    private EmailRepository emailRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    private User user;
    private Project project;
    private Device device;
    private Email email;

    @Before
    public void setUp() {
        user = new User();
        user.setUsername(username);
        user.setPassword(password);

        email = new Email();
        email.setAddress(address);

        user.setEmail(email);

        project = new Project();
        project.setName(name);
        project.setTitle(title);

        device = new Device();
        device.setState(device_state);
        device.setStates(device_states);
    }

    @Test
    public void injectedComponentsAreNotNull() {
        Assert.assertNotNull(projectRepository);
        Assert.assertNotNull(userRepository);
        Assert.assertNotNull(deviceRepository);
    }

    private User createUser(User u) {
        when(emailRepository.create(email)).thenReturn(email);
        when(emailRepository.update(email)).thenReturn(email);

        return userRepository.create(user);
    }

    @Test
    public void createWhenProjectsSizeIsZero() {
        User createdUser = createUser(user);

        Assert.assertEquals(0, projectRepository.getUserProjectsByUser(createdUser).size());

        Project createdProject = projectRepository.create(name, user.getUsername(), title);

        Assert.assertEquals(name, createdProject.getName());
        Assert.assertEquals(title, createdProject.getTitle());
        Assert.assertEquals(createdUser.getId(), createdProject.getUser().getId());
        Assert.assertNotNull(createdProject.getId());

        Assert.assertEquals(1, projectRepository.getUserProjectsByUser(createdUser).size());
    }

    @Test
    public void createWhenProjectsSizeIsOne() {
        User createdUser = createUser(user);
        Assert.assertEquals(0, projectRepository.getUserProjectsByUser(createdUser).size());

        Project createdProject = projectRepository.create(name, createdUser.getUsername(), title);
        Assert.assertEquals(1, projectRepository.getUserProjectsByUser(createdUser).size());

        Project secondProject = projectRepository.create(second_name, createdUser.getUsername(), second_title);
        Assert.assertEquals(2, projectRepository.getUserProjectsByUser(createdUser).size());
    }

    @Test
    public void update() {
        User createdUser = createUser(user);

        Project createdProject = projectRepository.create(name, createdUser.getUsername(), title);

        createdProject.setTitle(second_title);
        createdProject.setName(second_name);

        Project updatedProject = projectRepository.update(createdProject);

        Assert.assertEquals(second_name, updatedProject.getName());
        Assert.assertEquals(second_title, updatedProject.getTitle());
        Assert.assertEquals(createdProject.getId(), updatedProject.getId());
    }

    @Test
    public void getById() {
        User createdUser = createUser(user);

        Project createdProject = projectRepository.create(name, createdUser.getUsername(), title);

        Project fromDb = projectRepository.getById(createdProject.getId());

        Assert.assertEquals(createdProject.getName(), fromDb.getName());
        Assert.assertEquals(createdProject.getTitle(), fromDb.getTitle());
        Assert.assertEquals(createdProject.getId(), fromDb.getId());
    }

    @Test
    public void getUserProjectsByUser() {
        User createdUser = createUser(user);

        Assert.assertEquals(0, projectRepository.getUserProjectsByUser(createdUser).size());

        Project createdProject = projectRepository.create(name, createdUser.getUsername(), title);

        Set<Project> projects = projectRepository.getUserProjectsByUser(createdUser);

        Assert.assertEquals(1, projects.size());
        Assert.assertEquals(createdProject, projects.iterator().next());
    }

    @Test
    public void isExist() {
        User createdUser = createUser(user);

        Assert.assertFalse(projectRepository.isExist(1L));

        Project createdProject = projectRepository.create(name, createdUser.getUsername(), title);

        Assert.assertTrue(projectRepository.isExist(1L));
        Assert.assertTrue(projectRepository.isExist(createdProject.getId()));
    }

    @Test
    public void deleteWhenProjectEmpty() {
        User createdUser = createUser(user);

        Project createdProject = projectRepository.create(name, createdUser.getUsername(), title);

        Assert.assertTrue(projectRepository.isExist(createdProject.getId()));

        projectRepository.delete(createdProject.getId());

        Assert.assertFalse(projectRepository.isExist(createdProject.getId()));
    }

    @Test
    public void deleteWhenProjectNotEmpty() {
        User createdUser = createUser(user);

        Project createdProject = projectRepository.create(name, createdUser.getUsername(), title);
        Device createdDevice = deviceRepository.create(createdProject, device);

        Assert.assertTrue(projectRepository.isExist(createdProject.getId()));
        Assert.assertTrue(deviceRepository.isExist(createdDevice.getId()));

        projectRepository.delete(createdProject.getId());

        Assert.assertFalse(projectRepository.isExist(createdProject.getId()));
        Assert.assertFalse(deviceRepository.isExist(createdDevice.getId()));
    }
}
