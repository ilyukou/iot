package by.grsu.iot.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@PropertySource("classpath:application-api.properties")
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"by.grsu.iot"})
@EntityScan(basePackages = {"by.grsu.iot"})
@ComponentScan(basePackages = {"by.grsu.iot"})
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class);
    }
}
