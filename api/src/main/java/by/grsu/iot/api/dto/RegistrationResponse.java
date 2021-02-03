package by.grsu.iot.api.dto;

import by.grsu.iot.model.sql.User;

public class RegistrationResponse {

    private Long id;

    public RegistrationResponse(Long id) {
        this.id = id;
    }

    public RegistrationResponse(User user) {
        this.id = user.getId();
    }

    public RegistrationResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
