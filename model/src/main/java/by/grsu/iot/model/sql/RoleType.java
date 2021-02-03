package by.grsu.iot.model.sql;

public enum RoleType {
    User("ROLE_USER"),
    Admin("ROLE_ADMIN");

    private final String value;

    RoleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
