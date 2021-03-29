package by.grsu.iot.model.dto.thing.sensor;

import by.grsu.iot.model.elasticsearch.SensorValueElasticsearch;

/**
 * Used to send a value of sensor
 *
 * @author Ilyukou Ilya
 */
public class SensorValue {

    private Long time;
    private Double value;

    public SensorValue(Long time, Double value) {
        this.time = time;
        this.value = value;
    }

    public SensorValue(SensorValueElasticsearch sensorValueElasticsearch) {
        this.time = sensorValueElasticsearch.getTime();
        this.value = sensorValueElasticsearch.getValue();
    }

    public SensorValue() {
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
