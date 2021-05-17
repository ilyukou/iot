package by.grsu.iot.model.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = {"by.grsu.iot.model"})
@ComponentScan(basePackages = {"by.grsu.iot.model"})
public class ModelModuleConfig {
}
