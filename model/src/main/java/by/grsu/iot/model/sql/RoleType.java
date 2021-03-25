package by.grsu.iot.model.sql;

/**
 * RoleType for {@link Role}
 *
 * @author Ilyukou Ilya
 */
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
