package by.grsu.iot.api.model.dto.thing.sensor;

import by.grsu.iot.api.model.annotation.RequiredField;
import by.grsu.iot.api.model.elasticsearch.SensorValueElasticsearch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Used to send a value of sensor
 *
 * @author Ilyukou Ilya
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SensorValue {

    private Long time;

    @RequiredField
    private Double value;

    public SensorValue(SensorValueElasticsearch sensorValueElasticsearch) {
        this.time = sensorValueElasticsearch.getTime();
        this.value = sensorValueElasticsearch.getValue();
    }
}
