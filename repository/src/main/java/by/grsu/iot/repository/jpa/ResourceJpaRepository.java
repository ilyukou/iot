package by.grsu.iot.repository.jpa;

import by.grsu.iot.model.sql.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for {@link Resource}
 *
 * @author Ilyukou Ilya
 */
@Repository
public interface ResourceJpaRepository extends JpaRepository<Resource, Long> {

    @Query(value = "select r.id from resource r where r.filename = ?1", nativeQuery = true)
    Long getResourceIdByFilename(String filename);

}
