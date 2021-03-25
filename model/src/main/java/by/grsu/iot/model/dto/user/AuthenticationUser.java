package by.grsu.iot.model.dto.user;

import java.io.Serializable;

/**
 * The model uses, after successful user authorization, to pass the authorization token
 *
 * @author Ilyukou Ilya
 */
public class AuthenticationUser implements Serializable {

    private Long tokenValidity;
    private String username;
    private String token;

    public AuthenticationUser(String username, String token, Long tokenValidity) {
        this.username = username;
        this.token = token;
        this.tokenValidity = tokenValidity;
    }

    public AuthenticationUser() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getTokenValidity() {
        return tokenValidity;
    }

    public void setTokenValidity(Long tokenValidity) {
        this.tokenValidity = tokenValidity;
    }
}
