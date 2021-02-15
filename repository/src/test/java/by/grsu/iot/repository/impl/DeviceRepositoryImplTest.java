package by.grsu.iot.repository.impl;

import by.grsu.iot.model.sql.Device;
import by.grsu.iot.model.sql.Email;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.User;
import by.grsu.iot.repository.RepositoryApplication;
import by.grsu.iot.repository.factory.EntityFactory;
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
@SpringBootTest(classes = {RepositoryApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeviceRepositoryImplTest {

    @MockBean
    private EntityFactory entityFactory;

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

    @Before
    public void setUp(){
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
    public void injectedComponentsAreNotNull(){
        Assert.assertNotNull(projectRepository);
        Assert.assertNotNull(userRepository);
        Assert.assertNotNull(deviceRepository);
    }

    private User createUser(User u){
        when(entityFactory.createEmail(address)).thenReturn(email);
        when(emailRepository.create(email)).thenReturn(email);
        when(emailRepository.update(email)).thenReturn(email);

        return userRepository.create(user);
    }

    private Project createProject(String name, String username, String title){
        when(entityFactory.createProject()).thenReturn(new Project());

        return projectRepository.create(name, username, title);
    }

    @Test
    public void createWhenProjectEmpty(){
        User createdUser = createUser(user);
        Project createdProject = createProject(name, createdUser.getUsername(), title);
        when(entityFactory.createDevice()).thenReturn(device);

        Assert.assertFalse(deviceRepository.isExist(1l));

        Device createdDevice = deviceRepository.create(createdProject, device);

        Assert.assertEquals(device_state, device.getState());
        Assert.assertEquals(device_states, device.getStates());
        Assert.assertNotNull(device.getId());

        Project project = projectRepository.getById(createdProject.getId());
        Assert.assertEquals(1, project.getDevices().size());
    }

    @Test
    public void createWhenProjectNotEmpty(){
        User createdUser = createUser(user);
        Project createdProject = createProject(name, createdUser.getUsername(), title);
        when(entityFactory.createDevice()).thenReturn(device);

        Device createdDevice = deviceRepository.create(createdProject, device);
        Assert.assertEquals(1, projectRepository.getById(createdProject.getId()).getDevices().size());

        Device createdDevice2 = deviceRepository.create(createdProject, device);
        Assert.assertEquals(1, projectRepository.getById(createdProject.getId()).getDevices().size());
    }

    @Test
    public void  getById(){
        User createdUser = createUser(user);
        Project createdProject = createProject(name, createdUser.getUsername(), title);
        when(entityFactory.createDevice()).thenReturn(device);

        Assert.assertNull(deviceRepository.getById(1L));

        Device createdDevice = deviceRepository.create(createdProject, device);

        Assert.assertEquals(createdDevice, deviceRepository.getById(createdDevice.getId()));
    }

    @Test
    public void getDevicesByProject(){
        User createdUser = createUser(user);
        Project createdProject = createProject(name, createdUser.getUsername(), title);
        when(entityFactory.createDevice()).thenReturn(device);

        Device d1 = deviceRepository.create(createdProject, device);

        device = new Device();
        device.setState(device_state);
        device.setStates(device_states);
        when(entityFactory.createDevice()).thenReturn(device);

        Device d2 = deviceRepository.create(createdProject, device);

        Set<Device> devices = deviceRepository.getDevicesByProject(createdProject);

        Assert.assertEquals(2, devices.size());

        int count = 0;

        for (Device d: devices){
            if(d.getId().equals(d1.getId())){
                Assert.assertEquals(d1, d);
                count++;
            }

            if(d.getId().equals(d2.getId())){
                Assert.assertEquals(d2, d);
                count++;
            }
        }

        if(count != 2){
            Assert.fail();
        }
    }

    @Test
    public void update(){
        User createdUser = createUser(user);
        Project createdProject = createProject(name, createdUser.getUsername(), title);
        when(entityFactory.createDevice()).thenReturn(device);

        Device createdDevice = deviceRepository.create(createdProject, device);

        createdDevice.setState(second_device_state);

        Device updatedDevice = deviceRepository.update(createdDevice);

        Assert.assertEquals(second_device_state, updatedDevice.getState());
    }

    @Test
    public void getByToken(){
        User createdUser = createUser(user);
        Project createdProject = createProject(name, createdUser.getUsername(), title);
        when(entityFactory.createDevice()).thenReturn(device);
        Device createdDevice = deviceRepository.create(createdProject, device);

        Assert.assertEquals(createdDevice, deviceRepository.getByToken(createdDevice.getToken()));
    }

    @Test
    public void delete(){
        User createdUser = createUser(user);
        Project createdProject = createProject(name, createdUser.getUsername(), title);
        when(entityFactory.createDevice()).thenReturn(device);

        Assert.assertFalse(deviceRepository.delete(1L));

        Device createdDevice = deviceRepository.create(createdProject, device);

        Assert.assertTrue(deviceRepository.delete(1L));
    }

    @Test
    public void isExist(){
        User createdUser = createUser(user);
        Project createdProject = createProject(name, createdUser.getUsername(), title);
        when(entityFactory.createDevice()).thenReturn(device);

        Assert.assertFalse(deviceRepository.isExist(1L));

        Device createdDevice = deviceRepository.create(createdProject, device);

        Assert.assertTrue(deviceRepository.isExist(1L));
    }
}
