package by.grsu.iot.repository.elasticsearch;

import by.grsu.iot.model.elasticsearch.SensorValueElasticsearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link ElasticsearchRepository} for CRUD operation with {@link SensorValueElasticsearch}
 *
 * @author Ilyukou Ilya
 */
@Repository
public interface SensorValueElasticsearchRepository extends ElasticsearchRepository<SensorValueElasticsearch, String> {
}
