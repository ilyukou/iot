package by.grsu.iot.repository.jpa;

import by.grsu.iot.model.sql.RoleType;
import by.grsu.iot.model.sql.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query(value = "select p.id from project p where p.user_Id = ?1", nativeQuery = true)
    RoleType getUserRoleType(String username);
}
