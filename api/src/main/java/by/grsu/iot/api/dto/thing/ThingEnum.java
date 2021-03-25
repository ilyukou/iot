package by.grsu.iot.api.dto.thing;

public enum ThingEnum {
    device("device");

    private final String value;

    ThingEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
