package by.grsu.iot.model.dto.thing;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum of thing type
 *
 * @author Ilykou Ilya
 */
@Getter
@AllArgsConstructor
public enum ThingEnum {
    device("device"),
    sensor("sensor");

    private final String value;
}
