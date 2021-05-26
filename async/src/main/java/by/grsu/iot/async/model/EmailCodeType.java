package by.grsu.iot.async.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ilyukou Ilya
 */
@Getter
@AllArgsConstructor
public enum EmailCodeType {
    CREATE_ACCOUNT("CREATE_ACCOUNT"),
    RESTORE_PASSWORD("RESTORE_PASSWORD"),
    CHANGE_EMAIL("CHANGE_EMAIL");

    private final String operation;
}
