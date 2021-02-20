package by.grsu.iot.service;

import by.grsu.iot.repository.RepositoryApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Import(value = {RepositoryApplication.class})
@SpringBootApplication(scanBasePackages = {"by.grsu.iot.service"})
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class);
    }
}
