package by.grsu.iot.model.sql;

public enum Status {
    NOT_ACTIVE("NOT_ACTIVE"),
    ACTIVE("ACTIVE"),
    DISABLED("DISABLED"),
    DELETED("DELETED");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
