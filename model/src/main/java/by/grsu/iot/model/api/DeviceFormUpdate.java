package by.grsu.iot.model.api;

public class DeviceFormUpdate {
    private String name;

    public DeviceFormUpdate(String name) {
        this.name = name;
    }

    public DeviceFormUpdate() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
