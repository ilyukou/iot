package by.grsu.iot.model.sql;

/**
 * Access type for search engine
 */
public enum AccessType {
    PRIVATE("PRIVATE"),
    PUBLIC("PUBLIC");

    private final String value;

    AccessType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
