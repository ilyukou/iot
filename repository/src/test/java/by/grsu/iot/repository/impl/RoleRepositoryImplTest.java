package by.grsu.iot.repository.impl;

import by.grsu.iot.model.sql.Role;
import by.grsu.iot.model.sql.RoleType;
import by.grsu.iot.repository.RepositoryApplication;
import by.grsu.iot.repository.interf.RoleRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;


@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RepositoryApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoleRepositoryImplTest {

    private final RoleType ADMIN_ROLE_TYPE = RoleType.Admin;
    private final RoleType USER_ROLE_TYPE = RoleType.User;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void injectedComponentsAreNotNull() {
        Assert.assertNotNull(roleRepository);
    }


    @Test
    public void getRoleByRoleType() {
        Assert.assertNull(roleRepository.getRoleByRoleType(USER_ROLE_TYPE));
        Assert.assertNull(roleRepository.getRoleByRoleType(ADMIN_ROLE_TYPE));

        roleRepository.create(ADMIN_ROLE_TYPE);
        Assert.assertNull(roleRepository.getRoleByRoleType(USER_ROLE_TYPE));
        Assert.assertNotNull(roleRepository.getRoleByRoleType(ADMIN_ROLE_TYPE));

        roleRepository.create(USER_ROLE_TYPE);
        Assert.assertNotNull(roleRepository.getRoleByRoleType(USER_ROLE_TYPE));
        Assert.assertNotNull(roleRepository.getRoleByRoleType(ADMIN_ROLE_TYPE));
    }

    @Test
    public void getRoleOrCreate() {
        Role created = roleRepository.create(ADMIN_ROLE_TYPE);
        Role fromRepository = roleRepository.getRoleOrCreate(created.getRoleType());

        Assert.assertEquals(created.getRoleType(), fromRepository.getRoleType());
        Assert.assertEquals(created.getId(), fromRepository.getId());
        Assert.assertEquals(created.getCreated(), fromRepository.getCreated());
        Assert.assertEquals(created.getUpdated(), fromRepository.getUpdated());
    }

    @Test
    public void create() {
        Assert.assertNull(roleRepository.getRoleByRoleType(USER_ROLE_TYPE));
        Assert.assertNull(roleRepository.getRoleByRoleType(ADMIN_ROLE_TYPE));

        Role adminCreated = roleRepository.create(ADMIN_ROLE_TYPE);
        Assert.assertNull(roleRepository.getRoleByRoleType(USER_ROLE_TYPE));
        Assert.assertNotNull(roleRepository.getRoleByRoleType(ADMIN_ROLE_TYPE));

        Role userCreated = roleRepository.create(USER_ROLE_TYPE);
        Assert.assertNotNull(roleRepository.getRoleByRoleType(USER_ROLE_TYPE));
        Assert.assertNotNull(roleRepository.getRoleByRoleType(ADMIN_ROLE_TYPE));
    }
}
