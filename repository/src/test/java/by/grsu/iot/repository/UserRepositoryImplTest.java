package by.grsu.iot.repository;

import by.grsu.iot.repository.interf.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class UserRepositoryImplTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test(){
        System.out.println();
    }
}
