package by.grsu.iot.service.domain;

public class ValidationRule {
    private Integer min;
    private Integer max;
    private Boolean space;

    public ValidationRule(Integer min, Integer max, Boolean space) {
        this.min = min;
        this.max = max;
        this.space = space;
    }

    public ValidationRule() {
    }

    public Integer getMin() {
        return min;
    }

    public Integer getMax() {
        return max;
    }

    public Boolean getSpace() {
        return space;
    }
}