package by.grsu.iot.service.domain;

public class ValidationRule {
    private int min;
    private int max;
    private boolean space;

    public ValidationRule(int min, int max, boolean space) {
        this.min = min;
        this.max = max;
    }

    public ValidationRule() {
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }


    public boolean getSpace() {
        return space;
    }

    public void setSpace(boolean space) {
        this.space = space;
    }
}