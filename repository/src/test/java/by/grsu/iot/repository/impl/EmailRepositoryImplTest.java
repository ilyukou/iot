package by.grsu.iot.repository.impl;

import by.grsu.iot.model.sql.Email;
import by.grsu.iot.repository.RepositoryApplication;
import by.grsu.iot.repository.interf.EmailRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RepositoryApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmailRepositoryImplTest {

    @Autowired
    private EmailRepository emailRepository;

    private Email email;

    private final String address = "email@email.com";
    private final String second_address = "email2@email.com";

    @Before
    public void setUp(){
        email = new Email();
        email.setAddress(address);
    }

    @Test
    public void injectedComponentsAreNotNull(){
        Assert.assertNotNull(emailRepository);
    }

    @Test
    public void create(){
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
    public void findByAddress(){
        Assert.assertNull(emailRepository.findByAddress(address));
        Email created = emailRepository.create(email);

        Email fromDb = emailRepository.findByAddress(address);
        Assert.assertNotNull(fromDb);

        Assert.assertEquals(created.getId(), fromDb.getId());
        Assert.assertEquals(created.getUpdated(), fromDb.getUpdated());
        Assert.assertEquals(created.getCreated(), fromDb.getCreated());
    }

    @Test
    public void getById(){
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
    public void update(){
        Email created = emailRepository.create(email);

        created.setAddress(second_address);

        if(created.getUpdated().getTime() != created.getCreated().getTime()){
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

        if(updated.getUpdated().getTime() <= updated.getCreated().getTime()){
            Assert.fail();
        }
    }

    @Test
    public void isExist(){
        Assert.assertFalse(emailRepository.isExist(address));
        Email created = emailRepository.create(email);
        Assert.assertTrue(emailRepository.isExist(address));
    }
}
