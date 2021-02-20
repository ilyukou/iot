package by.grsu.iot.model.elastic;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "devicestate")
public class DeviceStateElasticsearch {

    @Id
    @Field(type = FieldType.Text, store = true)
    private String id;

    private String token;

    private String state;

    private long time;

    public DeviceStateElasticsearch(String id, String token, String state, long time) {
        this.id = id;
        this.token = token;
        this.state = state;
        this.time = time;
    }

    public DeviceStateElasticsearch(String token, String state, long time) {
        this.token = token;
        this.state = state;
        this.time = time;
    }

    public DeviceStateElasticsearch() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
