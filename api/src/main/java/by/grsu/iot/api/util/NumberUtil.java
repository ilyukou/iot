package by.grsu.iot.api.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NumberUtil {

    @Value("${by.grsu.iot.api.util.number}")
    private String NUMBER;


    public Integer generateNumber(long length) {
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
