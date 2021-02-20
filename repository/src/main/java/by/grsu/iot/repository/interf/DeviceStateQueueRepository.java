package by.grsu.iot.repository.interf;

import by.grsu.iot.model.elastic.DeviceStateElasticsearch;

/**
 * Repository for CRUD operation with {@link DeviceStateElasticsearch}
 * see also {@link by.grsu.iot.model.sql.Device}
 */
public interface DeviceStateQueueRepository {

    /**
     * Put {@link DeviceStateElasticsearch} int queue
     * @param deviceState to put
     */
    void put(DeviceStateElasticsearch deviceState);

    /**
     * Get {@link DeviceStateElasticsearch} by {@link DeviceStateElasticsearch#getToken()}
     * @param deviceToken {@link DeviceStateElasticsearch#getToken()}
     * @return {@link DeviceStateElasticsearch} from queue or {@code null}
     */
    DeviceStateElasticsearch get(String deviceToken);

    /**
     * Is exist {@link DeviceStateElasticsearch} by {@link DeviceStateElasticsearch#getToken()}
     * @param deviceToken {@link DeviceStateElasticsearch#getToken()}
     * @return {@code true} ir exist or {@code false}
     */
    boolean isExist(String deviceToken);

    /**
     * Delete {@link DeviceStateElasticsearch} by {@link DeviceStateElasticsearch#getToken()}
     * @param deviceToken {@link DeviceStateElasticsearch#getToken()}
     */
    void delete(String deviceToken);

    /**
     * Get {@link DeviceStateElasticsearch} from queue and delete
     * @param deviceToken {@link DeviceStateElasticsearch#getToken()}
     * @return {@link DeviceStateElasticsearch}
     */
    DeviceStateElasticsearch getAndDelete(String deviceToken);
}
