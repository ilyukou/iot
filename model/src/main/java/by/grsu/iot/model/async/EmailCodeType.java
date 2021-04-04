package by.grsu.iot.model.async;

public enum EmailCodeType {
    CREATE_ACCOUNT("CREATE_ACCOUNT"),
    RESTORE_PASSWORD("RESTORE_PASSWORD"),
    CHANGE_EMAIL("CHANGE_EMAIL");

    private final String operation;

    EmailCodeType(String operation) {
        this.operation = operation;
    }

    public String getValue() {
        return operation;
    }
}
