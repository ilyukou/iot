package by.grsu.iot.model.domain;

public enum DeviceStateResult {
    WAIT("WAIT"),
    COMPLETED("COMPLETED");

    private final String value;

    DeviceStateResult(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
