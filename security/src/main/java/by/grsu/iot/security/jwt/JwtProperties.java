package by.grsu.iot.security.jwt;

import by.grsu.iot.util.service.StringUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private static final Long SECRET_KEY_LENGTH = 32L;
    private static final Long VALIDITY_TOKEN_IN_MILLISECONDS = 100 * 3600000L; // 100h

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
