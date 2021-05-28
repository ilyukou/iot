package by.grsu.iot.api.repository;

import by.grsu.iot.api.config.RepositoryTestConfig;
import by.grsu.iot.api.model.sql.Device;
import by.grsu.iot.api.model.sql.Email;
import by.grsu.iot.api.model.sql.Project;
import by.grsu.iot.api.model.sql.User;
import by.grsu.iot.api.repository.sql.project.ProjectRepository;
import by.grsu.iot.api.repository.sql.project.thing.DeviceRepository;
import by.grsu.iot.api.repository.sql.user.EmailRepository;
import by.grsu.iot.api.repository.sql.user.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;

@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RepositoryTestConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeviceRepositoryImplTest {

    private final String username = "username";
    private final String password = "password";
    private final String address = "address";
    private final String name = "projectName";
    private final String title = "Title";
    private final String second_name = "secondProjectName";
    private final String second_title = "secondTitle";
    private final String device_state = "off";
    private final String second_device_state = "on";
    private final String device_token = "token";
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
        device.setToken(device_token);
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

    private Project createProject(String name, String username, String title) {

        return projectRepository.create(name, username, title);
    }

    @Test
    public void createWhenProjectEmpty() {
        User createdUser = createUser(user);
        Project createdProject = createProject(name, createdUser.getUsername(), title);

        Assert.assertFalse(deviceRepository.isExist(1l));

        Device createdDevice = deviceRepository.create(createdProject, device);

        Assert.assertEquals(device_state, createdDevice.getState());
        Assert.assertEquals(device_states, createdDevice.getStates());
        Assert.assertNotNull(createdDevice.getId());

        Project project = projectRepository.getById(createdProject.getId());
        Assert.assertEquals(1, projectRepository.getProjectIotThingSize(createdProject.getId()).longValue());
    }

    @Test
    public void createWhenProjectNotEmpty() {
        User createdUser = createUser(user);
        Project createdProject = createProject(name, createdUser.getUsername(), title);

        Device createdDevice = deviceRepository.create(createdProject, device);


        Assert.assertEquals(1, projectRepository.getProjectIotThingSize(createdProject.getId()).longValue());

        Device createdDevice2 = deviceRepository.create(createdProject, device);
        Assert.assertEquals(2, projectRepository.getProjectIotThingSize(createdProject.getId()).longValue());
    }

    @Test
    public void getById() {
        User createdUser = createUser(user);
        Project createdProject = createProject(name, createdUser.getUsername(), title);

        Assert.assertNull(deviceRepository.getById(1L));

        Device createdDevice = deviceRepository.create(createdProject, device);

        Assert.assertEquals(createdDevice, deviceRepository.getById(createdDevice.getId()));
    }

    @Test
    public void getDevicesByProject() {
        User createdUser = createUser(user);
        Project createdProject = createProject(name, createdUser.getUsername(), title);

        Device d1 = deviceRepository.create(createdProject, device);

        device = new Device();
        device.setState(device_state);
        device.setStates(device_states);

        Device d2 = deviceRepository.create(createdProject, device);

        Set<Device> devices = deviceRepository.getDevicesByProject(createdProject);

        Assert.assertEquals(2, devices.size());

        int count = 0;

        for (Device d : devices) {
            if (d.getId().equals(d1.getId())) {
                Assert.assertEquals(d1, d);
                count++;
            }

            if (d.getId().equals(d2.getId())) {
                Assert.assertEquals(d2, d);
                count++;
            }
        }

        if (count != 2) {
            Assert.fail();
        }
    }

//    @Test
//    public void update() {
//        User createdUser = createUser(user);
//        Project createdProject = createProject(name, createdUser.getUsername(), title);
//
//        Device createdDevice = deviceRepository.create(createdProject, device);
//
//        createdDevice.setState(second_device_state);
//
//        Device updatedDevice = deviceRepository.update(createdDevice);
//
//        Assert.assertEquals(second_device_state, updatedDevice.getState());
//    }

    @Test
    public void getByToken() {
        User createdUser = createUser(user);
        Project createdProject = createProject(name, createdUser.getUsername(), title);
        Device createdDevice = deviceRepository.create(createdProject, device);

        Assert.assertEquals(createdDevice, deviceRepository.getByToken(createdDevice.getToken()));
    }

    @Test
    public void delete() {
        User createdUser = createUser(user);
        Project createdProject = createProject(name, createdUser.getUsername(), title);

        Assert.assertFalse(deviceRepository.delete(1L));

        Device createdDevice = deviceRepository.create(createdProject, device);

        Assert.assertTrue(deviceRepository.delete(1L));
    }

//    @Test
//    public void isExist() {
//        User createdUser = createUser(user);
//        Project createdProject = createProject(name, createdUser.getUsername(), title);
//
//        Assert.assertFalse(deviceRepository.isExist(1L));
//
//        Device createdDevice = deviceRepository.create(createdProject, device);
//
//        Assert.assertTrue(deviceRepository.isExist(createdDevice.getId()));
//    }
}
