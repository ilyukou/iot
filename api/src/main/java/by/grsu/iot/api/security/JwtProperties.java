package by.grsu.iot.api.security;

import by.grsu.iot.api.util.StringUtil;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private final Long SECRET_KEY_LENGTH = 32L;

    private final Long VALIDITY_TOKEN_IN_MILLISECONDS = 360000000L;

    private final String secretKey;

    public JwtProperties(StringUtil stringUtil) {
        secretKey = stringUtil.generateString(SECRET_KEY_LENGTH);
    }

    public String getSecretKey() {
        return secretKey;
    }

    public long getValidityInMs() {
        return VALIDITY_TOKEN_IN_MILLISECONDS;
    }
}
