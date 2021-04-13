package by.grsu.iot.util.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * String Util
 *
 * @author Ilyukou Ilya
 */
@Component
@PropertySource("classpath:application-util.properties")
public class StringUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);

    private static String ALPHABET;

    @Value("${by.grsu.iot.util.alphabet}")
    public void set(String alphabet) {
        StringUtil.ALPHABET = alphabet;
    }

    public static String generateString(long length) {
        StringBuilder name = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * ALPHABET.length());
            name.append(
                    ALPHABET.charAt(index)
            );
        }
        return name.toString();
    }

    public static String generateFileName(long length, String filename) {

        if (length > 255 || length > ALPHABET.length()) {
            length = ALPHABET.length();
        }

        String contentType = getContentTypeFromFileName(filename);
        return generateString(length) + "." + contentType;
    }

    public static String getContentTypeFromFileName(String originalFileName) {
        String[] s = originalFileName.split("\\.");
        return s[1];
    }

    public static String generateToken(long length) {
        return generateString(length);
    }
}
