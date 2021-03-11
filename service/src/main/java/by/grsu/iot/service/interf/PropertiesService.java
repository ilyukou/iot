package by.grsu.iot.service.interf;

import java.util.Map;

/**
 * Service for getting application properties
 */
public interface PropertiesService {

    /**
     * Return {@link Map} of application properties
     *
     * @return {@link Map} of properties.
     */
    Map<String, Object> getProperties();
}
