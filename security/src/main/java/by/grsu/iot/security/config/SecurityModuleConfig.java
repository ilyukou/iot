package by.grsu.iot.security.config;

import by.grsu.iot.repository.config.RepositoryModuleConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import(RepositoryModuleConfig.class)
@ComponentScan(basePackages = {"by.grsu.iot.security"})
@PropertySource("classpath:application-security.properties")
public class SecurityModuleConfig {
}
