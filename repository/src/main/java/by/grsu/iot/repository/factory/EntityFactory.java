package by.grsu.iot.repository.factory;

import by.grsu.iot.model.activemq.*;
import by.grsu.iot.model.elastic.*;
import by.grsu.iot.model.sql.*;
import by.grsu.iot.repository.interf.RoleRepository;
import by.grsu.iot.repository.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    // BaseEntity default fields
    private static final Status DEFAULT_STATUS = Status.NOT_ACTIVE;

    // User default fields
    private static final RoleType DEFAULT_ROLE_TYPE = RoleType.User;

    // Project default fields
    private static final AccessType PROJECT_ACCESS_TYPE = AccessType.PRIVATE;
    private static final Status PROJECT_STATUS = Status.ACTIVE;

    private static final long EMAIL_VERIFICATION_CODE_LENGTH = 30;
    private static Long tokenLength;
    private static RoleRepository roleRepository;
    private static TimeUtil timeUtil;
    private static StringUtil stringUtil;
    @Value("${iot.sensor.token.length}")
    private Long tokenLen;

    private static BaseEntity createBaseEntity() {
        BaseEntity baseEntity = new BaseEntity();

        baseEntity.setStatus(DEFAULT_STATUS);

        Date date = timeUtil.getCurrentDate();

        baseEntity.setCreated(date);
        baseEntity.setUpdated(date);

        return baseEntity;
    }

    public static User createUser() {
        User user = new User(createBaseEntity());

        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.getRoleOrCreate(DEFAULT_ROLE_TYPE));
        user.setRoles(roles);

        return user;
    }

    public static Device createDevice(Status projectStatus) {
        Device sensor = new Device(createBaseEntity());

        sensor.setStatus(projectStatus);
        sensor.setToken(stringUtil.generateToken(tokenLength));

        // Default values
        sensor.addState("off");
        sensor.addState("on");

        // Default state
        sensor.setState("off");

        return sensor;
    }

    public static Device createDevice() {
        return createDevice(Status.ACTIVE);
    }

    public static Project createProject() {
        Project project = new Project(createBaseEntity());

        project.setStatus(PROJECT_STATUS);
        project.setAccessType(PROJECT_ACCESS_TYPE);

        return project;
    }

    public static Email createEmail() {
        return new Email(createBaseEntity());
    }

    public static Email createEmail(String address) {
        Email email = createEmail();

        email.setAddress(address);
        email.setCode(stringUtil.generateString(EMAIL_VERIFICATION_CODE_LENGTH));

        return email;
    }

    @Autowired
    public void setSomeThing(RoleRepository roleRepository, TimeUtil timeUtil, StringUtil stringUtil) {
        tokenLength = tokenLen;
        EntityFactory.roleRepository = roleRepository;
        EntityFactory.timeUtil = timeUtil;
        EntityFactory.stringUtil = stringUtil;
    }
}
