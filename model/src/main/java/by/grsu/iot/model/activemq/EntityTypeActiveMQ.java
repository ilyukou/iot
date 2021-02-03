package by.grsu.iot.model.activemq;

/**
 * This object shows which entity you need to work with
 */
public enum EntityTypeActiveMQ {
    PROJECT("PROJECT"),
    USER("USER");

    private final String value;

    EntityTypeActiveMQ(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
