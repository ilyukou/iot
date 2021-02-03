package by.grsu.iot.repository.jpa;

import by.grsu.iot.model.sql.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailJpaRepository extends JpaRepository<Email, Long> {
    Optional<Email> findEmailByAddress(String address);

    Optional<Email> findEmailByCode(String verificationCode);
}
