package by.grsu.iot.api.repository;

import by.grsu.iot.api.model.sql.*;
import by.grsu.iot.api.repository.sql.RoleRepository;
import by.grsu.iot.api.util.NumberUtil;
import by.grsu.iot.api.util.StringUtil;
import by.grsu.iot.api.util.TimeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

    //    private final RoleRepository roleRepository;
    private final StringUtil stringUtil;
    private final NumberUtil numberUtil;

    // BaseEntity default fields
    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    // User default fields
    private static final RoleType DEFAULT_ROLE_TYPE = RoleType.User;
    // Project default fields
    private static final Status PROJECT_STATUS = Status.ACTIVE;
    private static final String DEFAULT_STATE = "off";
    private static final List<String> DEFAULT_STATES = Arrays.asList("off", "on");
    @Value("${by.grsu.iot.repository.email.code.length}")
    private Long EMAIL_CODE_LENGTH_PROPERTY;
    @Value("${by.grsu.iot.repository.thing.token.length}")
    private Long THING_TOKEN_LENGTH_PROPERTY;

    public EntityFactory(NumberUtil numberUtil, RoleRepository roleRepository, StringUtil stringUtil) {
        this.numberUtil = numberUtil;
//        this.roleRepository = roleRepository;
        this.stringUtil = stringUtil;
    }


    private BaseEntity createBaseEntity() {
        BaseEntity baseEntity = new BaseEntity();

        baseEntity.setStatus(DEFAULT_STATUS);

        Date date = TimeUtil.getCurrentDate();

        baseEntity.setCreated(date);
        baseEntity.setUpdated(date);

        return baseEntity;
    }

    public User createUser(String address) {
        User user = new User(createBaseEntity());

        user.setStatus(Status.NOT_ACTIVE);

//        List<Role> roles = new ArrayList<>();
//        roles.add(roleRepository.getRoleOrCreate(DEFAULT_ROLE_TYPE));
//        user.setRoles(roles);

        user.setEmail(createEmail(address));

        return user;
    }

    public Device createDevice() {
        Device device = new Device(createBaseEntity());

        device.setToken(stringUtil.generateToken(THING_TOKEN_LENGTH_PROPERTY));

        device.setStates(DEFAULT_STATES);

        device.setState(DEFAULT_STATE);

        return device;
    }

    public Project createProject() {
        Project project = new Project(createBaseEntity());

        project.setStatus(PROJECT_STATUS);

        return project;
    }

    public Email createEmail(String address) {
        Email email = new Email(createBaseEntity());

        email.setAddress(address);
        email.setCode(numberUtil.generateNumber(EMAIL_CODE_LENGTH_PROPERTY));

        return email;
    }

    public Sensor createSensor() {
        Sensor sensor = new Sensor(createBaseEntity());

        sensor.setToken(stringUtil.generateToken(THING_TOKEN_LENGTH_PROPERTY));

        return sensor;
    }
}
