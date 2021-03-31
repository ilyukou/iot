package by.grsu.iot.service;

import by.grsu.iot.access.AccessApplication;
import by.grsu.iot.repository.RepositoryApplication;
import by.grsu.iot.security.SecurityApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

@Import(value = {RepositoryApplication.class, AccessApplication.class, SecurityApplication.class})
@SpringBootApplication
@EnableCaching
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class);
    }
}
