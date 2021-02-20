package by.grsu.iot.telegram.message.impl;

import by.grsu.iot.telegram.message.interf.ProjectTelegramMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:application-telegram-message.properties")
public class ProjectTelegramMessageServiceImpl implements ProjectTelegramMessageService {

    @Value("${project.welcome}")
    private String welcome;

    @Override
    public String getWelcomeText() {
        return welcome;
    }
}
