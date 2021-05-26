package by.grsu.iot.api.model.sql;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ilyukou Ilya
 */
@Getter
@AllArgsConstructor
public enum Status {
    NOT_ACTIVE("NOT_ACTIVE"),
    ACTIVE("ACTIVE"),
    DISABLED("DISABLED"),
    DELETED("DELETED");

    private final String value;
}
