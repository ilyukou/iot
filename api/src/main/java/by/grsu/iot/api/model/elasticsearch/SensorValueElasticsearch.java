package by.grsu.iot.api.model.elasticsearch;

import by.grsu.iot.api.model.sql.Sensor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Objects;

/**
 * Document containing values from sensors {@link Sensor}
 *
 * @author Ilyukou Ilya
 */
@Document(indexName = "sensorvalue")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SensorValueElasticsearch implements Comparable {

    @Id
    @Field(type = FieldType.Text, store = true)
    private String id;

    private Long time;

    private Double value;

    private String token;

    public SensorValueElasticsearch(Long time, Double value, String token) {
        this.time = time;
        this.value = value;
        this.token = token;
    }

    @Override
    public int compareTo(Object o) {
        SensorValueElasticsearch obj = (SensorValueElasticsearch) o;

        if (getTime().equals(obj.getTime())) {
            return 0;
        } else if (getTime() > obj.getTime()) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SensorValueElasticsearch)) return false;
        SensorValueElasticsearch that = (SensorValueElasticsearch) o;
        return Objects.equals(getTime(), that.getTime())
//                && Objects.equals(getId(), that.getId())
                && Objects.equals(getValue(), that.getValue())
                && Objects.equals(getToken(), that.getToken());
    }
}