package by.grsu.iot.api.permission.resolver;

import by.grsu.iot.model.sql.User;
import org.springframework.web.bind.annotation.RestController;

/**
 * Permission Resolver for any {@link RestController}.
 *
 * @author Ilyukou Ilya
 */
public interface PermissionResolver {

    /**
     * Support the resolver that {@link RestController}
     *
     * @param targetType {@link Class#getName()}}
     * @return true if support
     */
    boolean support(String targetType);

    /**
     * Has permission the {@link User} create entity.
     *
     * @param username {@link User#getUsername()}
     * @param parentId parent id.
     * @return true if it can
     */
    boolean hasPermissionCreate(String username, Long parentId);

    /**
     * Has permission the {@link User} read entity.
     *
     * @param username {@link User#getUsername()}
     * @param id       entity id.
     * @return true if it can
     */
    boolean hasPermissionRead(String username, Long id);

    /**
     * Has permission the {@link User} read page of entity.
     *
     * @param username {@link User#getUsername()}
     * @param parentId parent id.
     * @return true if it can
     */
    boolean hasPermissionReadPage(String username, Long parentId);

    /**
     * Has permission the {@link User} update entity.
     *
     * @param username {@link User#getUsername()}
     * @param id       entity id.
     * @return true if it can
     */
    boolean hasPermissionUpdate(String username, Long id);

    /**
     * Has permission the {@link User} delete entity.
     *
     * @param username {@link User#getUsername()}
     * @param id       entity id.
     * @return true if it can
     */
    boolean hasPermissionDelete(String username, Long id);
}
