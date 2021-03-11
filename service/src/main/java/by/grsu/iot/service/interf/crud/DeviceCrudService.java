package by.grsu.iot.service.interf.crud;

import by.grsu.iot.model.sql.Device;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.User;
import by.grsu.iot.service.domain.form.DeviceForm;
import by.grsu.iot.service.domain.form.DeviceFormUpdate;

/**
 * Service layer for CRUD operation with {@link Device}
 */
public interface DeviceCrudService {

    /**
     * Create {@link Device} for {@link Project} with such {@link Project#getId()}.
     *
     * @param deviceForm form for created
     * @param username   who request a create device {@link User#getUsername()}
     * @return created {@link Device}
     */
    Device create(DeviceForm deviceForm, String username);

    /**
     * Get {@link Device} by {@link Device#getId()}
     *
     * @param id {@link Device#getId()}
     * @param username who request a device {@link User#getUsername()}
     * @return {@link Device}
     */
    Device getById(Long id, String username);

    /**
     * Delete {@link Device} by {@link Device#getId()}
     *
     * @param id {@link Device#getId()}
     * @param username who request a delete device {@link User#getUsername()}
     */
    void deleteById(Long id, String username);

    /**
     * Update {@link Device}
     *
     * @param id {@link Device#getId()}
     * @param deviceFormUpdate updated field
     * @param username who request a update device {@link User#getUsername()}
     * @return updated {@link Device}
     */
    Device update(Long id, DeviceFormUpdate deviceFormUpdate, String username);

    /**
     * Get {@link Device} by {@link Device#getToken()}
     *
     * @param token {@link Device#getToken()}
     * @return {@link Device}
     */
    Device getByToken(String token);
}