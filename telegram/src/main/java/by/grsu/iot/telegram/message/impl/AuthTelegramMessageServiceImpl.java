package by.grsu.iot.telegram.message.impl;

import by.grsu.iot.telegram.message.interf.AuthTelegramMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:application-telegram-message.properties")
public class AuthTelegramMessageServiceImpl implements AuthTelegramMessageService {

    @Value("${auth.welcome}")
    private String welcomeText;

    @Value("${auth.credential}")
    private String credentialText;

    @Value("${auth.not-found-user}")
    private String userNotFoundText;

    @Override
    public String getWelcomeText() {
        return welcomeText;
    }

    @Override
    public String getCredentialText() {
        return credentialText;
    }

    @Override
    public String getUserNotFoundText() {
        return userNotFoundText;
    }
}
