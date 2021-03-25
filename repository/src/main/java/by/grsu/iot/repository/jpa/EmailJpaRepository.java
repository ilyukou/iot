package by.grsu.iot.repository.jpa;

import by.grsu.iot.model.sql.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA Repository for {@link Email}
 *
 * @author Ilyukou Ilya
 */
@Repository
public interface EmailJpaRepository extends JpaRepository<Email, Long> {
    Optional<Email> findEmailByAddress(String address);

    Optional<Email> findEmailByCode(String verificationCode);

    boolean existsByAddress(String address);
}
