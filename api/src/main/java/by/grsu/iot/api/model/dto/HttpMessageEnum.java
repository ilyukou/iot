package by.grsu.iot.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ilyukou Ilya
 */
@Getter
@AllArgsConstructor
public enum HttpMessageEnum {
    ok("ok"),
    info("info"),
    error("error"),
    warn("warn");

    private final String value;
}
