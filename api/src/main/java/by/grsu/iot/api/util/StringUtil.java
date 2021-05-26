package by.grsu.iot.api.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * String Util
 *
 * @author Ilyukou Ilya
 */
@Component
public class StringUtil {

    @Value("${by.grsu.iot.repository.string.alphabet}")
    private String ALPHABET;

    public String generateString(long length) {
        StringBuilder name = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * ALPHABET.length());
            name.append(
                    ALPHABET.charAt(index)
            );
        }
        return name.toString();
    }

    public String generateFileName(long length, String filename) {

        if (length > 255 || length > ALPHABET.length()) {
            length = ALPHABET.length();
        }

        String contentType = getContentTypeFromFileName(filename);
        return generateString(length) + "." + contentType;
    }

    public String getContentTypeFromFileName(String originalFileName) {
        String[] s = originalFileName.split("\\.");
        return s[1];
    }

    public String generateToken(long length) {
        return generateString(length);
    }
}
