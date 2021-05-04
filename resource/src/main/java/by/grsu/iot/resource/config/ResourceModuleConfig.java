package by.grsu.iot.resource.config;

import by.grsu.iot.util.config.UtilModuleConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(UtilModuleConfig.class)
@ComponentScan(basePackages = {"by.grsu.iot.resource"})
public class ResourceModuleConfig {
}
