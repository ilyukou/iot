package by.grsu.iot.repository.factory;

import by.grsu.iot.model.sql.*;
import by.grsu.iot.repository.interf.RoleRepository;
import by.grsu.iot.repository.util.StringUtil;
import by.grsu.iot.repository.util.TimeUtil;
import by.grsu.iot.util.service.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Basic setting of the object.
 *
 * @author Ilyukou Ilya
 */
@Component
public class EntityFactory {

    // BaseEntity default fields
    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    // User default fields
    private static final RoleType DEFAULT_ROLE_TYPE = RoleType.User;
    // Project default fields
    private static final Status PROJECT_STATUS = Status.ACTIVE;
    private static final String DEFAULT_STATE = "off";
    private static final List<String> DEFAULT_STATES = Arrays.asList("off", "on");
    private static RoleRepository roleRepository;
    private static Integer EMAIL_CODE_LENGTH;

    private static Long THING_TOKEN_LENGTH;

    private static BaseEntity createBaseEntity() {
        BaseEntity baseEntity = new BaseEntity();

        baseEntity.setStatus(DEFAULT_STATUS);

        Date date = TimeUtil.getCurrentDate();

        baseEntity.setCreated(date);
        baseEntity.setUpdated(date);

        return baseEntity;
    }

    public static User createUser(String address) {
        User user = new User(createBaseEntity());

        user.setStatus(Status.NOT_ACTIVE);

        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.getRoleOrCreate(DEFAULT_ROLE_TYPE));
        user.setRoles(roles);

        user.setEmail(createEmail(address));

        return user;
    }

    public static Device createDevice() {
        Device device = new Device(createBaseEntity());

        device.setToken(StringUtil.generateToken(THING_TOKEN_LENGTH));

        device.setStates(DEFAULT_STATES);

        device.setState(DEFAULT_STATE);

        return device;
    }

    public static Project createProject() {
        Project project = new Project(createBaseEntity());

        project.setStatus(PROJECT_STATUS);

        return project;
    }

    public static Email createEmail(String address) {
        Email email = new Email(createBaseEntity());

        email.setAddress(address);
        email.setCode(NumberUtil.generateNumber(EMAIL_CODE_LENGTH));

        return email;
    }

    public static Sensor createSensor() {
        Sensor sensor = new Sensor(createBaseEntity());

        sensor.setToken(StringUtil.generateToken(THING_TOKEN_LENGTH));

        return sensor;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        EntityFactory.roleRepository = roleRepository;
    }

    @Value("${by.grsu.iot.repository.email.code.length}")
    public void setEmailTokenLength(Integer emailCodeLength) {
        EntityFactory.EMAIL_CODE_LENGTH = emailCodeLength;
    }

    @Value("${by.grsu.iot.repository.device.token.length}")
    public void setThingTokenLength(Long thingTokenLength) {
        EntityFactory.THING_TOKEN_LENGTH = thingTokenLength;
    }
}
