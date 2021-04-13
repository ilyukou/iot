package by.grsu.iot.util.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application-util.properties")
public class NumberUtil {

    private static String NUMBER = "0123456789";

    @Value("${by.grsu.iot.util.number}")
    public void set(String number) {
        NumberUtil.NUMBER = number;
    }

    public static Integer generateNumber(long length){
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
