package by.grsu.iot.repository.interf;

import by.grsu.iot.model.elastic.DeviceState;

/**
 * Repository for CRUD operation with {@link DeviceState}
 * see also {@link by.grsu.iot.model.sql.Device}
 */
public interface DeviceStateQueueRepository {

    /**
     * Put {@link DeviceState} int queue
     * @param deviceState to put
     */
    void put(DeviceState deviceState);

    /**
     * Get {@link DeviceState} by {@link DeviceState#getToken()}
     * @param deviceToken {@link DeviceState#getToken()}
     * @return {@link DeviceState} from queue or {@code null}
     */
    DeviceState get(String deviceToken);

    /**
     * Is exist {@link DeviceState} by {@link DeviceState#getToken()}
     * @param deviceToken {@link DeviceState#getToken()}
     * @return {@code true} ir exist or {@code false}
     */
    boolean isExist(String deviceToken);

    /**
     * Delete {@link DeviceState} by {@link DeviceState#getToken()}
     * @param deviceToken {@link DeviceState#getToken()}
     */
    void delete(String deviceToken);

    /**
     * Get {@link DeviceState} from queue and delete
     * @param deviceToken {@link DeviceState#getToken()}
     * @return {@link DeviceState}
     */
    DeviceState getAndDelete(String deviceToken);
}
