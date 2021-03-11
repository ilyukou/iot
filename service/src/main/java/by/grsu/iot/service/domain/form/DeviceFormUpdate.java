package by.grsu.iot.service.domain.form;

import javax.validation.constraints.NotNull;

public class DeviceFormUpdate implements Form {

    @NotNull
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
