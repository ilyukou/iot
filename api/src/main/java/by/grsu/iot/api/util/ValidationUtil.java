package by.grsu.iot.api.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ValidationUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ValidationUtil.class);

    @Value("${project.name.length.min}")
    private Long minProjectNameSize;

    @Value("${project.name.length.max}")
    private Long maxProjectNameSize;

    @Value("${sensor.name.length.min}")
    private Long minSensorNameSize;

    @Value("${sensor.name.length.max}")
    private Long maxSensorNameSize;

    public boolean isUnValidNameForProject(String name) {
        if (name.length() > maxProjectNameSize) {
            String ms = "Length should be less " + maxProjectNameSize + " characters";
            LOG.info(ms);
            throw new IllegalArgumentException(ms);
        }

        if (name.length() < minProjectNameSize) {
            String ms = "Length must be more than " + minProjectNameSize + " characters";
            LOG.info(ms);
            throw new IllegalArgumentException(ms);
        }

        return true;
    }

    public boolean isUnValidNameForSensor(String name) {

        if (name.length() > maxSensorNameSize) {
            String ms = "Length should be less " + maxSensorNameSize + " characters";
            LOG.info(ms);
            throw new IllegalArgumentException(ms);
        }

        if (name.length() < minSensorNameSize) {
            String ms = "Length must be more than " + minSensorNameSize + " characters";
            LOG.info(ms);
            throw new IllegalArgumentException(ms);
        }

        return true;
    }
}
