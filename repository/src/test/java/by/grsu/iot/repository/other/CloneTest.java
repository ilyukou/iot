package by.grsu.iot.repository.other;

import by.grsu.iot.model.sql.Device;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.User;
import by.grsu.iot.repository.RepositoryApplication;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RepositoryApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CloneTest {

    private final String DEVICE = "device";
    private final String PROJECT = "project";
    private final String USER = "user";

    private final String DEVICE_SECOND = "deviceSecond";
    private final String PROJECT_SECOND = "projectSecond";
    private final String USER_SECOND = "userSecond";

    @Test
    public void constructorClone(){
        Device device = new Device();
        device.setName(DEVICE);

        Project project = new Project();
        project.setName(PROJECT);

        project.setDevices(Set.of(device));

        User user = new User();
        user.setUsername(USER);

        user.setProjects(Set.of(project));

        User another = SerializationUtils.clone(user);

        another = changeParams(another);

        Assert.assertEquals(USER, user.getUsername());
        Assert.assertEquals(PROJECT, user.getProjects().iterator().next().getName());
        Assert.assertEquals(DEVICE, user.getProjects().iterator().next().getDevices().iterator().next().getName());

        Assert.assertEquals(USER_SECOND, another.getUsername());
        Assert.assertEquals(PROJECT_SECOND, another.getProjects().iterator().next().getName());
        Assert.assertEquals(DEVICE_SECOND, another.getProjects().iterator().next().getDevices().iterator().next().getName());
    }

    private User changeParams(final User u){
        User clone = new User(u);
        clone.setUsername(USER_SECOND);

        Set<Project> projects = clone.getProjects();
        Project project = projects.iterator().next();
        project.setName(PROJECT_SECOND);

        Device device = project.getDevices().iterator().next();
        device.setName(DEVICE_SECOND);

        return clone;
    }
}
