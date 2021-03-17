package by.grsu.iot.service.domain.response;

public enum HttpMessageEnum {
    ok("ok"),
    info("info"),
    error("error"),
    warn("warn");

    private final String value;

    HttpMessageEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
