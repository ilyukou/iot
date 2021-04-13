package by.grsu.iot.service.interf.crud;

import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.Resource;
import by.grsu.iot.model.sql.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * Service layer for CRUD operation with {@link Resource}
 *
 * @author Ilyukou Ilya
 */
public interface ResourceCrudService {

    /**
     * Create a resource for {@link Project}
     * @param file {@link MultipartFile} from controller layer
     * @param projectId {@link Project#getId()}
     * @param username {@link User#getUsername()}
     * @return created {@link Project}
     */
    Resource create(MultipartFile file, Long projectId, String username);

    /**
     * Get resource by {@link Resource#getFileName()}
     * @param filename {@link Resource#getFileName()}
     * @return {@link InputStream}
     */
    InputStream get(String filename, String username);

    /**
     * Does file extension supports
     * @param type file extension
     * @return true if support
     */
    Boolean support(String type);
}
