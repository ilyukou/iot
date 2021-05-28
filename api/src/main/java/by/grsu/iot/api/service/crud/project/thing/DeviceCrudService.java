package by.grsu.iot.api.service.crud.project.thing;

import by.grsu.iot.api.model.dto.sort.RequestSortType;
import by.grsu.iot.api.model.dto.thing.device.DeviceForm;
import by.grsu.iot.api.model.dto.thing.device.DeviceFormUpdate;
import by.grsu.iot.api.model.sql.Device;
import by.grsu.iot.api.model.sql.Project;
import org.springframework.data.domain.Page;

/**
 * Service layer for CRUD operation with {@link Device}
 *
 * @author Ilyukou Ilya
 */
public interface DeviceCrudService {

    /**
     * Create {@link Device} for {@link Project} with such {@link Project#getId()}.
     *
     * @param deviceForm form with fields
     * @return created {@link Device}
     */
    Device create(DeviceForm deviceForm);

    /**
     * Get {@link Device} by {@link Device#getId()}
     *
     * @param id {@link Device#getId()}
     * @return {@link Device}
     */
    Device getById(Long id);

    /**
     * Delete {@link Device} by {@link Device#getId()}
     *
     * @param id {@link Device#getId()}
     */
    void delete(Long id);

    /**
     * Update {@link Device}
     *
     * @param id               {@link Device#getId()}
     * @param deviceFormUpdate form with fields
     * @return updated {@link Device}
     */
    Device update(Long id, DeviceFormUpdate deviceFormUpdate);

    /**
     * Get current {@link Device#getState()} from repository
     *
     * @param token {@link Device#getToken()}
     * @return {@link Device#getState()}
     */
    String getDeviceState(String token);

    Page<Device> getPage(Long project, Integer size, Integer page, RequestSortType type, String field);
}