package by.grsu.iot.service.interf;

import java.io.IOException;
import java.util.Map;

/**
 * Service for getting application properties
 *
 * @author Ilyukou Ilya
 */
public interface PropertiesService {

    /**
     * Return {@link Map} of application properties
     *
     * @return {@link Map} of properties.
     */
    Map<String, Object> getProperties() throws IOException;
}
