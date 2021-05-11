package by.grsu.iot.repository.impl;

import by.grsu.iot.model.sql.Email;
import by.grsu.iot.model.sql.Role;
import by.grsu.iot.model.sql.RoleType;
import by.grsu.iot.model.sql.User;
import by.grsu.iot.repository.RepositoryApplication;
import by.grsu.iot.repository.config.RepositoryTestConfig;
import by.grsu.iot.repository.interf.EmailRepository;
import by.grsu.iot.repository.interf.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@Import(RepositoryTestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RepositoryApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRepositoryImplTest {

    private final String username = "username";
    private final String username_second = "username_second";
    private final String password = "password";
    private final String address = "email@email.com";
    private final RoleType roleType = RoleType.User;
    private final Long EMAIL_ID = 1L;

    @MockBean
    private EmailRepository emailRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Email email;
    private Role role;

    @Before
    public void setUp() {
        user = new User();
        user.setUsername(username);
        user.setPassword(password);

        email = new Email();
        email.setAddress(address);
//        email.setId(EMAIL_ID);

        user.setEmail(email);

        role = new Role();
        role.setRole(roleType);
    }

    @Test
    public void injectedComponentsAreNotNull() {
        Assert.assertNotNull(userRepository);
    }

    @Test
    public void create() {
        when(emailRepository.create(email)).thenReturn(email);
        when(emailRepository.update(email)).thenReturn(email);

        User actualUser = userRepository.create(user);

        Assert.assertEquals(user.getUsername(), actualUser.getUsername());
        Assert.assertEquals(user.getPassword(), actualUser.getPassword());
        Assert.assertEquals(user.getEmail().getAddress(), actualUser.getEmail().getAddress());
        Assert.assertNotNull(actualUser.getId());
    }

    @Test
    public void getById() {
        when(emailRepository.create(email)).thenReturn(email);
        when(emailRepository.update(email)).thenReturn(email);

        User created = userRepository.create(user);
        User actual = userRepository.getById(created.getId());

        Assert.assertEquals(created.getId(), actual.getId());
        Assert.assertEquals(created.getUsername(), actual.getUsername());
        Assert.assertEquals(created.getPassword(), actual.getPassword());
        Assert.assertEquals(created.getEmail().getAddress(), actual.getEmail().getAddress());
    }

    @Test
    public void update() {
        when(emailRepository.create(email)).thenReturn(email);
        when(emailRepository.update(email)).thenReturn(email);

        User created = userRepository.create(user);

        User before = userRepository.getById(created.getId());

        created.setUsername(username_second);
        User after = userRepository.update(created);

        Assert.assertEquals(before.getEmail().getAddress(), after.getEmail().getAddress());
        Assert.assertEquals(username_second, after.getUsername());

        if (after.getUpdated().getTime() < before.getUpdated().getTime()) {
            Assert.fail();
        }
    }

    @Test
    public void getByUsername() {
        when(emailRepository.create(email)).thenReturn(email);
        when(emailRepository.update(email)).thenReturn(email);

        User created = userRepository.create(user);
        User expected = userRepository.getById(created.getId());

        Assert.assertEquals(created.getId(), expected.getId());
        Assert.assertEquals(created.getUsername(), expected.getUsername());
        Assert.assertEquals(created.getPassword(), expected.getPassword());
        Assert.assertEquals(created.getEmail().getAddress(), expected.getEmail().getAddress());
        Assert.assertEquals(created.getId(), expected.getId());
    }

    @Test
    public void isExistByUsername() {
        Assert.assertFalse(userRepository.isExistByUsername(username));

        when(emailRepository.create(email)).thenReturn(email);
        when(emailRepository.update(email)).thenReturn(email);

        User created = userRepository.create(user);

        Assert.assertTrue(userRepository.isExistByUsername(username));
    }

    @Test
    public void getEmailId(){
        when(emailRepository.create(email)).thenReturn(email);
        when(emailRepository.update(email)).thenReturn(email);

        User created = userRepository.create(user);
        when(emailRepository.create(email)).thenReturn(email);

        Assert.assertEquals(EMAIL_ID, userRepository.getEmailId(created.getUsername()));
    }
}
