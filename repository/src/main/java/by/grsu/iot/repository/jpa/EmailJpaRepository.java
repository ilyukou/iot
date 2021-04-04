package by.grsu.iot.repository.jpa;

import by.grsu.iot.model.sql.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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

    @Modifying
    @Query(value = "update email e set e.address = ?2, e.code = ?3 where e.id = ?1", nativeQuery = true)
    void changeAddress(Long id, String address, Integer code);

    @Query(value = "select e.code from email e where e.id = ?1", nativeQuery = true)
    Integer getEmailCode(Long id);

    @Modifying
    @Query(value = "update email e set e.code = ?2 where e.address = ?1", nativeQuery = true)
    void setCode(String address, Integer code);
}
