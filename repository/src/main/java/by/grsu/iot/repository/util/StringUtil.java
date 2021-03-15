package by.grsu.iot.repository.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StringUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);

    private static String alphabet;

    @Value("${iot.string.alphabet}")
    private void set(String alphabet){
        StringUtil.alphabet = alphabet;
    }

    public StringUtil() {
    }

    public static String generateString(long length) {
        StringBuilder name = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * alphabet.length());
            name.append(
                    alphabet.charAt(index)
            );
        }
        return name.toString();
    }

    public static String generateToken(long length) {
        return generateString(length);
    }
}
