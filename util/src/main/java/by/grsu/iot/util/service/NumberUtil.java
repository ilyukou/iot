package by.grsu.iot.util.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application-util.properties")
public class NumberUtil {

    private static String number = "0123456789"; // FIXME

    public static Integer generateNumber(long length){
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * number.length());
            str.append(
                    number.charAt(index)
            );
        }

        return Integer.valueOf(str.toString());
    }

    @Value("${by.grsu.iot.util.number}")
    private void set(String number) {
        NumberUtil.number = number;
    }
}
