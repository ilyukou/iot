package by.grsu.iot.async;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AsyncApplication {
    public static void main(String[] args) {
        SpringApplication.run(AsyncApplication.class);
    }
}
