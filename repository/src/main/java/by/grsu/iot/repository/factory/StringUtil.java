package by.grsu.iot.repository.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StringUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);
    @Value("${iot.string.alphabet}")
    private String alphabet;

    public StringUtil() {
    }

    public String generateString(long length) {
        StringBuilder name = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * alphabet.length());
            name.append(
                    alphabet.charAt(index)
            );
        }
        return name.toString();
    }

    public String generateToken(long length) {
        String token = generateString(length);

//        if (sensorRepository.isExist(token)) {
//            String ms = "Token with such name exist. Token={" + token + "}";
//            LOGGER.warn(ms);
//            throw new RuntimeException(ms);
//        }

        return token;
    }
}
