package by.grsu.iot.repository;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;


@SpringBootApplication(scanBasePackages = {"by.grsu.iot.repository", "by.grsu.iot.model"})
@EntityScan(basePackages = {"by.grsu.iot.model"})
@EnableJpaRepositories(basePackages = {"by.grsu.iot.repository"})
public class RepositoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(RepositoryApplication.class);
    }

    @PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        LoggerFactory.getLogger(RepositoryApplication.class)
                .info("Spring boot application running in UTC timezone :" + new Date());
    }
}
