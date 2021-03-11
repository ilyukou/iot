package by.grsu.iot.service.impl;

import by.grsu.iot.service.interf.PropertiesService;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@PropertySource("classpath:application-service.properties")
@Service
public class PropertiesServiceImpl implements PropertiesService {

    private Resource getResource() {

        // classpath:application-service.properties
        String value = this.getClass().getAnnotation(PropertySource.class).value()[0];

        // application-service.properties
        String fileName = value.substring("classpath:".length());

        return new ClassPathResource(fileName);
    }

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> map = new HashMap<>();
        Resource resource = getResource();
        Properties props;

        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
            for (Object key : props.keySet()) {
                map.put(key.toString(), cast(props.getProperty(key.toString())));
            }

            return map;
        } catch (IOException e) {
            return new HashMap<>();
        }
    }

    private Object cast(String value) {
        try {

            if (value.equals("true") || value.equals("false")) {
                return Boolean.parseBoolean(value);
            }

            return Long.parseLong(value);

        } catch (NumberFormatException e) {
            return value;
        }
    }
}
