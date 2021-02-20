package by.grsu.iot.telegram.domain;

public class KeyboardButton {

    private String label;
    private String state;

    public KeyboardButton(String label, String state) {
        this.label = label;
        this.state = state;
    }

    public String getLabel() {
        return label;
    }

    public String getState() {
        return state;
    }
}
