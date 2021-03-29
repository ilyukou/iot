package by.grsu.iot.repository.interf;

import by.grsu.iot.model.elasticsearch.SensorValueElasticsearch;

import java.util.List;

public interface SensorValueRepository {
    void add(String token, Long time, Double value);

    List<SensorValueElasticsearch> get(String token, Long from, Long to);

    SensorValueElasticsearch get(String token);

    Long getDocumentSize(String token);
}
