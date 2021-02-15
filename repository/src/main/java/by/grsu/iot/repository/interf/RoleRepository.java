package by.grsu.iot.repository.interf;

import by.grsu.iot.model.sql.Role;
import by.grsu.iot.model.sql.RoleType;
import org.springframework.stereotype.Repository;

/**
 * Repository for CRUD operation with {@link Role}
 */
public interface RoleRepository {

    /**
     * Get {@link Role} by {@link RoleType}
     * @param roleType {@link Role#getRoleType()}
     * @return {@link Role}
     */
    Role getRoleByRoleType(final RoleType roleType);

    /**
     * Get {@link Role} or {@link RoleRepository#create(RoleType)} a new
     * @param roleType {@link Role#getRoleType()}
     * @return {@link Role} from repository or created {@link Role}
     */
    Role getRoleOrCreate(final RoleType roleType);

    /**
     * Create {@link Role}
     * @param roleType {@link Role#getRoleType()}
     * @return created {@link Role}
     */
    Role create(final RoleType roleType);
}
