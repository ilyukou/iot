package by.grsu.iot.api.repository.sql.jpa;

import by.grsu.iot.api.model.sql.Project;
import by.grsu.iot.api.model.sql.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * JPA Repository for {@link Project}
 *
 * @author Ilyukou Ilya
 */
@Repository
public interface ProjectJpaRepository extends JpaRepository<Project, Long> {

    @Query(value = "select p.id from project p where p.user_Id = ?1", nativeQuery = true)
    List<Long> findProjectsIdByUserId(Long userId);

    Optional<Set<Project>> findProjectsByUser(User user);

    @Query(value = "select p.user_id from project p where p.id = ?1", nativeQuery = true)
    Long findUserId(Long projectId);

    @Query(value = "select p.user_id from project p where p.resource_id = ?1", nativeQuery = true)
    Long findUserIdByResourceId(Long projectId);

    Page<Project> findAllByUser(User user, Pageable pageable);
}