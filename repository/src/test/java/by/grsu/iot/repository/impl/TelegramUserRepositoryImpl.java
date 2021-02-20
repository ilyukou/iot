package by.grsu.iot.repository.impl;

import by.grsu.iot.repository.RepositoryApplication;
import by.grsu.iot.repository.interf.TelegramUserRepository;
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
public class TelegramUserRepositoryImpl {

    @Autowired
    private TelegramUserRepository telegramUserRepository;

    private org.telegram.telegrambots.meta.api.objects.User telegramRawUser;

    @Test
    public void injectedComponentsAreNotNull(){
        Assert.assertNotNull(telegramUserRepository);
    }

    @Test
    public void create(){

    }
}
