package by.grsu.iot.model.sql;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * RoleType for {@link Role}
 *
 * @author Ilyukou Ilya
 */
@Getter
@AllArgsConstructor
public enum RoleType {
    User("ROLE_USER"),
    Admin("ROLE_ADMIN");

    private final String value;

}
