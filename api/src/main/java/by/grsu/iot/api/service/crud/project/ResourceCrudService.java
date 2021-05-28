package by.grsu.iot.api.service.crud.project;

import by.grsu.iot.api.model.sql.Project;
import by.grsu.iot.api.model.sql.Resource;
import by.grsu.iot.api.model.sql.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

/**
 * Service layer for CRUD operation with {@link Resource}
 *
 * @author Ilyukou Ilya
 */
public interface ResourceCrudService {

    /**
     * Create a resource for {@link Project}
     *
     * @param file      {@link MultipartFile} from controller layer
     * @param projectId {@link Project#getId()}
     * @param username  {@link User#getUsername()}
     * @return created {@link Project}
     */
    @Deprecated(forRemoval = true)
    Resource create(MultipartFile file, Long projectId, String username);

    /**
     * Create {@link Resource} to {@link Project} with such {@link Project#getId()}
     *
     * @param project {@link Project#getId()}
     * @param request request with multipart content
     * @return created {@link Resource}
     * @throws IOException exception while parsing content
     */
    Resource create(Long project, HttpServletRequest request) throws IOException;

    /**
     * Get resource by {@link Resource#getFileName()}
     *
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
