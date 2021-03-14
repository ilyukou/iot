package by.grsu.iot.repository.jpa;

import by.grsu.iot.model.sql.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query(value = "select u.username from user u where u.id = ?1", nativeQuery = true)
    String findUsername(Long userId);

    @Query(value = "select u.id from user u where u.username = ?1", nativeQuery = true)
    Long findUserId(String username);
}
