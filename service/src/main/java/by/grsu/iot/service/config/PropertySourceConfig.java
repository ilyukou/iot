package by.grsu.iot.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application-service.properties")
@PropertySource("classpath:application-security.properties")
@PropertySource("classpath:application-service.properties")
@PropertySource("classpath:application-validation.properties")
public class PropertySourceConfig {
}
