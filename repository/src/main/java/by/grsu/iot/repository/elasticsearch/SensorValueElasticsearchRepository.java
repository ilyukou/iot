package by.grsu.iot.repository.elasticsearch;

import by.grsu.iot.model.elasticsearch.SensorValueElasticsearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorValueElasticsearchRepository extends ElasticsearchRepository<SensorValueElasticsearch, String> {
}
