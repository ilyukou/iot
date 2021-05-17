package by.grsu.iot.repository.config;

import by.grsu.iot.model.config.ModelModuleConfig;
import by.grsu.iot.util.config.UtilModuleConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"by.grsu.iot.repository"})
@Import({UtilModuleConfig.class, ModelModuleConfig.class})
@PropertySource("classpath:application-repository.properties")
public class RepositoryModuleConfig {
}
