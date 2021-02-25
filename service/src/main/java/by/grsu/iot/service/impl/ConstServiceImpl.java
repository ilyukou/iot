package by.grsu.iot.service.impl;

import by.grsu.iot.service.interf.ConstService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
public class ConstServiceImpl implements ConstService {

    private final static String PROPERTIES_FILE_NAME="application-const.properties";

    @Override
    public Map<String, String> getConstFields() {
        Map<String,String> map = new HashMap<>();
        Resource resource = new ClassPathResource(PROPERTIES_FILE_NAME);
        Properties props = null;

        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
            for(Object key : props.keySet()) {
                map.put(key.toString(), props.getProperty(key.toString()));
            }
            return map;
        } catch (IOException e) {
            return new HashMap<>();
        }
    }
}
