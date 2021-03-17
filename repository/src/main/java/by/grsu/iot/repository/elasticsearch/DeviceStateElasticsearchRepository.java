package by.grsu.iot.repository.elasticsearch;

import by.grsu.iot.model.elastic.DeviceStateElasticsearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceStateElasticsearchRepository extends ElasticsearchRepository<DeviceStateElasticsearch, String> {
    
    DeviceStateElasticsearch findByToken(String token);

    void deleteByToken(String token);
}
