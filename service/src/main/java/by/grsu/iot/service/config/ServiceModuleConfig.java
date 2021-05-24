package by.grsu.iot.service.config;

import by.grsu.iot.producer.config.ProducerModuleConfig;
import by.grsu.iot.repository.config.RepositoryModuleConfig;
import by.grsu.iot.resource.config.ResourceModuleConfig;
import by.grsu.iot.security.config.SecurityModuleConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {RepositoryModuleConfig.class, SecurityModuleConfig.class,
        ProducerModuleConfig.class, ResourceModuleConfig.class})
@EnableCaching
@ComponentScan(basePackages = {"by.grsu.iot.service"})
public class ServiceModuleConfig {
}
