package by.grsu.iot.telegram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@PropertySource("classpath:application-telegram.properties")
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"by.grsu.iot.telegram", "by.grsu.iot.service"})
public class TelegramApplication {
    public static void main(String[] args) {
        SpringApplication.run(TelegramApplication.class);
    }
}
