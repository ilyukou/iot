package by.grsu.iot.model.elasticsearch;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "sensorvalue")
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

    public SensorValueElasticsearch() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int compareTo(Object o) {
        SensorValueElasticsearch obj = (SensorValueElasticsearch) o;

        if (getTime().equals(obj.getTime())){
            return 0;
        } else if (getTime() > obj.getTime()){
            return 1;
        } else {
            return -1;
        }
    }
}