package by.grsu.iot.repository.factory;

import by.grsu.iot.model.sql.*;
import by.grsu.iot.repository.interf.RoleRepository;
import by.grsu.iot.repository.util.StringUtil;
import by.grsu.iot.repository.util.TimeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Basic setting of the object.
 */
@Component
public class EntityFactory {

    private final RoleRepository roleRepository;

    // BaseEntity default fields
    private final Status DEFAULT_STATUS = Status.ACTIVE;

    // User default fields
    private final RoleType DEFAULT_ROLE_TYPE = RoleType.User;

    // Project default fields
    private final AccessType PROJECT_ACCESS_TYPE = AccessType.PRIVATE;
    private final Status PROJECT_STATUS = Status.ACTIVE;

    private final String DEFAULT_STATE = "off";
    private final List<String> DEFAULT_STATES = Arrays.asList("off", "on");

    @Value("${by.grsu.iot.repository.email.token.length}")
    private Integer EMAIL_TOKEN_LENGTH;

    @Value("${by.grsu.iot.repository.device.token.length}")
    private Long DEVICE_TOKEN_LENGTH;

    public EntityFactory(
            RoleRepository roleRepository
    ) {
        this.roleRepository = roleRepository;
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

        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.getRoleOrCreate(DEFAULT_ROLE_TYPE));
        user.setRoles(roles);

        user.setEmail(createEmail(address));

        return user;
    }

    public Device createDevice() {
        Device device = new Device(createBaseEntity());

        device.setToken(StringUtil.generateToken(DEVICE_TOKEN_LENGTH));

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
        email.setCode(StringUtil.generateString(EMAIL_TOKEN_LENGTH));

        return email;
    }
}
