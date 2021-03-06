package by.grsu.iot.api;

import by.grsu.iot.email.EmailApplication;
import by.grsu.iot.security.SecurityApplication;
import by.grsu.iot.service.ServiceApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(value = {ServiceApplication.class, SecurityApplication.class, EmailApplication.class})
@SpringBootApplication
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class);
    }
}
