package by.grsu.iot.repository.jpa;

import by.grsu.iot.model.sql.Role;
import by.grsu.iot.model.sql.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleJpaRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(RoleType roleType);
}
