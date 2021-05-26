package by.grsu.iot.api.repository.sql.jpa;

import by.grsu.iot.api.model.sql.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA Repository for {@link User}
 *
 * @author Ilyukou Ilya
 */
@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query(value = "select u.username from user u where u.id = ?1", nativeQuery = true)
    Optional<String> findUsername(Long userId);

    @Query(value = "select u.id from user u where u.username = ?1", nativeQuery = true)
    Optional<Long> findUserId(String username);

    @Query(value = "select u.email_id from user u where u.username = ?1", nativeQuery = true)
    Long getEmailId(String username);
}
