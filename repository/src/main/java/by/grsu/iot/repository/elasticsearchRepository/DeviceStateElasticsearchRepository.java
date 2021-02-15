package by.grsu.iot.repository.elasticsearchRepository;

import by.grsu.iot.model.elastic.DeviceState;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceStateElasticsearchRepository extends ElasticsearchRepository<DeviceState, String> {
    
    DeviceState findByToken(String token);

    void deleteByToken(String token);
}
