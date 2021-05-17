package by.grsu.iot.async;

import by.grsu.iot.email.config.EmailModuleConfig;
import by.grsu.iot.service.config.ServiceModuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Import(value = {ServiceModuleConfig.class, EmailModuleConfig.class})
@SpringBootApplication
public class AsyncApplication {
    public static void main(String[] args) {
        SpringApplication.run(AsyncApplication.class);
    }
}
