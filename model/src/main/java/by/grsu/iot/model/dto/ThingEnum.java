package by.grsu.iot.model.dto;

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
