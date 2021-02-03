package by.grsu.iot.repository.util;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TimeUtil {

    public Date getCurrentDate() {
        return new Date();
    }
}
