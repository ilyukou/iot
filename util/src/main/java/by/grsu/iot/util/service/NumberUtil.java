package by.grsu.iot.util.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application-util.properties")
public class NumberUtil {

    private static final String MODULE = "by.grsu.iot.";

    private static final String NUMBER_PROPERTY = MODULE + "util.number";

    private static String NUMBER;

    @Autowired
    public NumberUtil(Environment environment) {
        NUMBER = environment.getProperty(NUMBER_PROPERTY);
    }

    public static Integer generateNumber(long length) {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * NUMBER.length());
            str.append(
                    NUMBER.charAt(index)
            );
        }

        return Integer.valueOf(str.toString());
    }
}
