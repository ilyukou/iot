package by.grsu.iot.repository.jpa;

import by.grsu.iot.model.sql.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface TelegramUserJpaRepository extends JpaRepository<TelegramUser, Long> {
    Optional<TelegramUser> findTelegramUserByUserId(Integer userId);

    boolean existsByUserId(Integer userId);
}
