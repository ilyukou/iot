package by.grsu.iot.api.dto;

/**
 * The model uses, after successful user authorization, to pass the authorization token
 */
public class AuthenticationUser {

    private Long id;
    private String username;
    private String token;

    public AuthenticationUser(String username, String token, Long id) {
        this.username = username;
        this.token = token;
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
