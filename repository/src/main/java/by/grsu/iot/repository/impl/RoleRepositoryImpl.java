package by.grsu.iot.repository.impl;

import by.grsu.iot.model.sql.Role;
import by.grsu.iot.model.sql.RoleType;
import by.grsu.iot.repository.interf.RoleRepository;
import by.grsu.iot.repository.jpa.RoleJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class RoleRepositoryImpl implements RoleRepository {

    private final RoleJpaRepository roleJpaRepository;

    public RoleRepositoryImpl(RoleJpaRepository roleJpaRepository) {
        this.roleJpaRepository = roleJpaRepository;
    }

    @Override
    public Role getRoleByRoleType(final RoleType roleType) {
        return roleJpaRepository.findByRole(roleType).orElse(null);
    }

    @Override
    public Role getRoleOrCreate(final RoleType roleType) {
        Role role = getRoleByRoleType(roleType);

        if (role != null) {
            return role;
        }

        return create(roleType);
    }

    @Override
    public Role create(final RoleType roleType) {
        return roleJpaRepository.save(new Role(roleType));
    }
}
