package by.grsu.iot.repository.jpa;

import by.grsu.iot.model.sql.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectJpaRepository extends JpaRepository<Project, Long> {

    @Query(value = "select p.id from project p where p.user_Id = ?1", nativeQuery = true)
    List<Long> findProjectsIdByUserId(Long userId);
}
