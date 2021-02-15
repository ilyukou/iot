package by.grsu.iot.repository.factory;

import by.grsu.iot.model.sql.*;
import by.grsu.iot.repository.interf.RoleRepository;
import by.grsu.iot.repository.util.TimeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Basic setting of the object.
 */
@Component
public class EntityFactory {

    private final TimeUtil timeUtil;
    private final StringUtil stringUtil;

    // BaseEntity default fields
    private final Status DEFAULT_STATUS = Status.NOT_ACTIVE;

    // User default fields
    private final RoleType DEFAULT_ROLE_TYPE = RoleType.User;

    // Project default fields
    private final AccessType PROJECT_ACCESS_TYPE = AccessType.PRIVATE;
    private final Status PROJECT_STATUS = Status.ACTIVE;

    private final long EMAIL_VERIFICATION_CODE_LENGTH = 30;

    @Value("${iot.sensor.token.length}")
    private Long tokenLen;

    public EntityFactory(TimeUtil timeUtil, StringUtil stringUtil) {
        this.timeUtil = timeUtil;
        this.stringUtil = stringUtil;
    }

    private BaseEntity createBaseEntity() {
        BaseEntity baseEntity = new BaseEntity();

        baseEntity.setStatus(DEFAULT_STATUS);

        Date date = timeUtil.getCurrentDate();

        baseEntity.setCreated(date);
        baseEntity.setUpdated(date);

        return baseEntity;
    }

    public User createUser() {
        User user = new User(createBaseEntity());

//        List<Role> roles = new ArrayList<>();
//        roles.add(roleRepository.getRoleOrCreate(DEFAULT_ROLE_TYPE));
//        user.setRoles(roles);

        return user;
    }

    public Device createDevice(Status status) {
        Device sensor = new Device(createBaseEntity());

        sensor.setStatus(status);
        sensor.setToken(stringUtil.generateToken(tokenLen));

        // Default values
        sensor.addState("off");
        sensor.addState("on");

        // Default state
        sensor.setState("off");

        return sensor;
    }

    public Device createDevice() {
        return createDevice(Status.ACTIVE);
    }

    public Project createProject() {
        Project project = new Project(createBaseEntity());

        project.setStatus(PROJECT_STATUS);
        project.setAccessType(PROJECT_ACCESS_TYPE);

        return project;
    }

    public Email createEmail() {
        return new Email(createBaseEntity());
    }

    public Email createEmail(String address) {
        Email email = createEmail();

        email.setAddress(address);
        email.setCode(stringUtil.generateString(EMAIL_VERIFICATION_CODE_LENGTH));

        return email;
    }
}
