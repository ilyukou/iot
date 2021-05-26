package by.grsu.iot.api.repository;

import by.grsu.iot.api.config.RepositoryTestConfig;
import by.grsu.iot.api.model.sql.Role;
import by.grsu.iot.api.model.sql.RoleType;
import by.grsu.iot.api.repository.sql.RoleRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RepositoryTestConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
        Role fromRepository = roleRepository.getRoleOrCreate(created.getRole());

        Assert.assertEquals(created.getRole(), fromRepository.getRole());
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