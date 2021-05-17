package by.grsu.iot.repository.impl;

import by.grsu.iot.model.sql.Email;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.Sensor;
import by.grsu.iot.model.sql.User;
import by.grsu.iot.repository.config.RepositoryTestConfig;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.repository.interf.SensorRepository;
import by.grsu.iot.repository.interf.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RepositoryTestConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SensorRepositoryImplTest {

    private final String username = "username";
    private final String password = "password";
    private final String address = "address";
    private final String name = "projectName";
    private final String title = "Title";

    private final String sensorName = "sensorName";
    private final String secondSensorName = "secondSensorName";

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SensorRepository sensorRepository;

    private User user;
    private Project project;
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

        user = userRepository.create(user);
        project = projectRepository.create(name, user.getUsername(), title);
    }

    @Test
    public void injectedComponentsAreNotNull() {
        Assert.assertNotNull(projectRepository);
        Assert.assertNotNull(userRepository);
        Assert.assertNotNull(sensorRepository);
    }

    @Test
    public void getOwnerUsername() {
        Sensor sensor = sensorRepository.create(project.getId(), sensorName);

        Assert.assertEquals(user.getUsername(), sensorRepository.getOwnerUsername(sensor.getId()));
    }

    @Test
    public void create() {
        Assert.assertEquals(0L, sensorRepository.getSensorsSize(project.getId()).longValue());

        Sensor sensor = sensorRepository.create(project.getId(), sensorName);

        Assert.assertEquals(1L, sensorRepository.getSensorsSize(project.getId()).longValue());
        Assert.assertTrue(sensorRepository.isExist(sensor.getId()));
        Assert.assertTrue(sensorRepository.isExist(sensor.getToken()));
        Assert.assertEquals(sensor, sensorRepository.getById(sensor.getId()));
    }

    @Test
    public void getById() {

        Sensor sensor = sensorRepository.create(project.getId(), sensorName);
        Sensor sensor2 = sensorRepository.create(project.getId(), secondSensorName);

        Assert.assertEquals(sensor, sensorRepository.getById(sensor.getId()));
        Assert.assertEquals(sensor2, sensorRepository.getById(sensor2.getId()));
    }

    @Test
    public void delete() {
        Assert.assertEquals(0L, sensorRepository.getSensorsSize(project.getId()).longValue());

        Sensor sensor = sensorRepository.create(project.getId(), sensorName);

        Assert.assertEquals(1L, sensorRepository.getSensorsSize(project.getId()).longValue());

        Sensor sensor2 = sensorRepository.create(project.getId(), secondSensorName);

        Assert.assertEquals(2L, sensorRepository.getSensorsSize(project.getId()).longValue());

        sensorRepository.delete(sensor.getId());

        Assert.assertEquals(1L, sensorRepository.getSensorsSize(project.getId()).longValue());
        Assert.assertFalse(sensorRepository.isExist(sensor.getId()));
        Assert.assertTrue(sensorRepository.isExist(sensor2.getId()));
    }

    @Test
    public void update() {

        Sensor sensor = sensorRepository.create(project.getId(), sensorName);

        Sensor beforeUpd = sensorRepository.getById(sensor.getId());

        sensor.setName(secondSensorName);

        Sensor afterUpd = sensorRepository.update(sensor);

        Assert.assertNotEquals(beforeUpd, sensorRepository.getById(sensor.getId()));
        Assert.assertEquals(afterUpd, sensorRepository.getById(sensor.getId()));
    }

    @Test
    public void isExistById() {

        Sensor sensor = sensorRepository.create(project.getId(), sensorName);

        Assert.assertTrue(sensorRepository.isExist(sensor.getId()));

        Sensor sensor2 = sensorRepository.create(project.getId(), secondSensorName);

        Assert.assertTrue(sensorRepository.isExist(sensor2.getId()));
    }

    @Test
    public void isExistByToken() {
        Sensor sensor = sensorRepository.create(project.getId(), sensorName);

        Assert.assertTrue(sensorRepository.isExist(sensor.getToken()));

        Sensor sensor2 = sensorRepository.create(project.getId(), secondSensorName);

        Assert.assertTrue(sensorRepository.isExist(sensor2.getToken()));
    }

    @Test
    public void getByToken() {
        Sensor sensor = sensorRepository.create(project.getId(), sensorName);
        Sensor sensor2 = sensorRepository.create(project.getId(), secondSensorName);

        Assert.assertEquals(sensor, sensorRepository.getByToken(sensor.getToken()));
        Assert.assertEquals(sensor2, sensorRepository.getByToken(sensor2.getToken()));
    }

    @Test
    public void getProjectSensorIds() {
        Sensor s1 = sensorRepository.create(project.getId(), sensorName);

        List<Long> result = sensorRepository.getProjectSensorIds(project.getId());
        Assert.assertEquals(1L, result.size());
        Assert.assertEquals(s1.getId(), result.get(0));

        Sensor s2 = sensorRepository.create(project.getId(), secondSensorName);

        List<Long> result2 = sensorRepository.getProjectSensorIds(project.getId());
        Assert.assertEquals(2L, result2.size());

        Assert.assertTrue(result2.contains(s1.getId()));
        Assert.assertTrue(result2.contains(s2.getId()));
    }

    @Test
    public void getSensorsSize() {
        Assert.assertEquals(0L, sensorRepository.getSensorsSize(project.getId()).longValue());

        Sensor s1 = sensorRepository.create(project.getId(), sensorName);

        Assert.assertEquals(1L, sensorRepository.getSensorsSize(project.getId()).longValue());

        Sensor s2 = sensorRepository.create(project.getId(), secondSensorName);

        Assert.assertEquals(2L, sensorRepository.getSensorsSize(project.getId()).longValue());
    }
}
