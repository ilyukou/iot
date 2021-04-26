package by.grsu.iot.repository.factory;

import by.grsu.iot.model.sql.*;
import by.grsu.iot.repository.interf.RoleRepository;
import by.grsu.iot.util.service.NumberUtil;
import by.grsu.iot.util.service.StringUtil;
import by.grsu.iot.util.service.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Basic setting of the object.
 *
 * @author Ilyukou Ilya
 */
@Component
public class EntityFactory {

    private static final String EMAIL_CODE_LENGTH_PROPERTY = "by.grsu.iot.repository.email.code.length";
    private static final String THING_TOKEN_LENGTH_PROPERTY = "by.grsu.iot.repository.thing.token.length";

    private static Long EMAIL_CODE_LENGTH;
    private static Long THING_TOKEN_LENGTH;

    // BaseEntity default fields
    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    // User default fields
    private static final RoleType DEFAULT_ROLE_TYPE = RoleType.User;
    // Project default fields
    private static final Status PROJECT_STATUS = Status.ACTIVE;
    private static final String DEFAULT_STATE = "off";
    private static final List<String> DEFAULT_STATES = Arrays.asList("off", "on");
    private static RoleRepository roleRepository;
    private static StringUtil stringUtil;

    @Autowired
    public EntityFactory(Environment environment) {
        EMAIL_CODE_LENGTH = Long.valueOf(Objects.requireNonNull(environment.getProperty(EMAIL_CODE_LENGTH_PROPERTY)));
        THING_TOKEN_LENGTH = Long.valueOf(Objects.requireNonNull(environment.getProperty(THING_TOKEN_LENGTH_PROPERTY)));
    }


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

        device.setToken(stringUtil.generateToken(THING_TOKEN_LENGTH));

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

        sensor.setToken(stringUtil.generateToken(THING_TOKEN_LENGTH));

        return sensor;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        EntityFactory.roleRepository = roleRepository;
    }

    @Autowired
    public void setRoleRepository(StringUtil stringUtil) {
        EntityFactory.stringUtil = stringUtil;
    }
}
