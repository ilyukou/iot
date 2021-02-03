package by.grsu.iot.model.activemq;

/**
 * This object was indicate what consumer need to do with entity
 */
public enum ActActiveMQ {
    CREATE("CREATE"),
    UPDATE("UPDATE"),
    DELETE("DELETE");

    private final String value;

    ActActiveMQ(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
