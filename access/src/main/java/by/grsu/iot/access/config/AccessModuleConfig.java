package by.grsu.iot.access.config;

import by.grsu.iot.repository.config.RepositoryModuleConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {RepositoryModuleConfig.class})
@ComponentScan(basePackages = {"by.grsu.iot.access"})
public class AccessModuleConfig {
}
