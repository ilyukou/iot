package by.grsu.iot.model.dto.thing;

/**
 * Enum of thing type
 *
 * @author Ilykou Ilya
 */
public enum ThingEnum {
    device("device"),
    sensor("sensor");

    private final String value;

    ThingEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
