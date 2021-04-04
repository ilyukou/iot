package by.grsu.iot.repository.impl;

import by.grsu.iot.model.sql.Email;
import by.grsu.iot.repository.RepositoryApplication;
import by.grsu.iot.repository.interf.EmailRepository;
import by.grsu.iot.repository.interf.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.when;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RepositoryApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmailRepositoryImplTest {

    private final String address = "email@email.com";
    private final String second_address = "email2@email.com";
    private final String username = "username";
    private final Integer code = 123456;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private EmailRepository emailRepository;
    private Email email;

    @Before
    public void setUp() {
        email = new Email();
        email.setAddress(address);
        email.setCode(code);
    }

    @Test
    public void injectedComponentsAreNotNull() {
        Assert.assertNotNull(emailRepository);
    }

    @Test
    public void create() {
        Email created = emailRepository.create(email);

        Assert.assertEquals(email.getAddress(), created.getAddress());
        Assert.assertNotNull(created.getCreated());
        Assert.assertNotNull(created.getUpdated());

        // When create Email update time and create time is equals
        Assert.assertEquals(created.getUpdated(), created.getCreated());
        Assert.assertNotNull(created.getId());

        Long id = 1L;
        Assert.assertEquals(id, created.getId());
    }

    @Test
    public void findByAddress() {
        Assert.assertNull(emailRepository.findByAddress(address));
        Email created = emailRepository.create(email);

        Email fromDb = emailRepository.findByAddress(address);
        Assert.assertNotNull(fromDb);

        Assert.assertEquals(created.getId(), fromDb.getId());
        Assert.assertEquals(created.getUpdated(), fromDb.getUpdated());
        Assert.assertEquals(created.getCreated(), fromDb.getCreated());
    }

    @Test
    public void getById() {
        Long id = 1L;
        Assert.assertNull(emailRepository.getById(id));
        Email created = emailRepository.create(email);

        Email fromDb = emailRepository.getById(id);
        Assert.assertNotNull(fromDb);

        Assert.assertEquals(created.getId(), fromDb.getId());
        Assert.assertEquals(created.getUpdated(), fromDb.getUpdated());
        Assert.assertEquals(created.getCreated(), fromDb.getCreated());
    }

    @Test
    public void update() {
        Email created = emailRepository.create(email);

        created.setAddress(second_address);

        if (created.getUpdated().getTime() != created.getCreated().getTime()) {
            Assert.fail();
        }

        try {
            TimeUnit.MICROSECONDS.sleep(100l);
        } catch (InterruptedException e) {
            // ignore
        }

        Email updated = emailRepository.update(created);

        Assert.assertEquals(created.getId(), updated.getId());
        Assert.assertEquals(second_address, updated.getAddress());

        if (updated.getUpdated().getTime() <= updated.getCreated().getTime()) {
            Assert.fail();
        }
    }

    @Test
    public void isExist() {
        Assert.assertFalse(emailRepository.isExist(address));
        Email created = emailRepository.create(email);
        Assert.assertTrue(emailRepository.isExist(address));
    }

    @Test
    public void changeAddress(){
        Email created = emailRepository.create(email);

        when(userRepository.getEmailId(username)).thenReturn(created.getId());

        Assert.assertEquals(address, emailRepository.getById(created.getId()).getAddress());
        emailRepository.changeAddress(username, second_address);
        Assert.assertEquals(second_address, emailRepository.getById(created.getId()).getAddress());
    }

    @Test
    public void getCode(){
        Email created = emailRepository.create(email);
        when(userRepository.getEmailId(username)).thenReturn(created.getId());

        Assert.assertEquals(code, emailRepository.getCode(username));
    }

    @Test
    public void createCode(){
        Email created = emailRepository.create(email);
        when(userRepository.getEmailId(username)).thenReturn(created.getId());
        Assert.assertEquals(code, emailRepository.getCode(username));

        emailRepository.createCode(created.getAddress());

        Assert.assertNotNull(emailRepository.getCode(username));
        Assert.assertNotEquals(code, emailRepository.getCode(username));
    }
}
