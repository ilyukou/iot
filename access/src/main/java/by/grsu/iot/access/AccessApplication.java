package by.grsu.iot.access;

import by.grsu.iot.repository.RepositoryApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(value = {RepositoryApplication.class})
@SpringBootApplication
public class AccessApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccessApplication.class);
    }
}
