package by.grsu.iot.repository.interf;

import by.grsu.iot.model.sql.Role;
import by.grsu.iot.model.sql.RoleType;

public interface RoleRepository {
    Role getRoleByRoleType(RoleType roleType);

    Role getRoleOrCreate(RoleType roleType);

    Role create(RoleType roleType);
}
