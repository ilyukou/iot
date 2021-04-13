package by.grsu.iot.repository.impl;

import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.Resource;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.repository.interf.ResourceRepository;
import by.grsu.iot.repository.jpa.ResourceJpaRepository;
import by.grsu.iot.util.service.TimeUtil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
@Repository
public class ResourceRepositoryImpl implements ResourceRepository {

    private final ResourceJpaRepository resourceJpaRepository;
    private final ProjectRepository projectRepository;

    public ResourceRepositoryImpl(
            ResourceJpaRepository resourceJpaRepository,
            ProjectRepository projectRepository
    ) {
        this.resourceJpaRepository = resourceJpaRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public Resource create(String fileName, Long projectId) {
        Project project = projectRepository.getById(projectId);

        Resource resource = new Resource(fileName, project);

        Date date = TimeUtil.getCurrentDate();

        resource.setUpdated(date);
        resource.setCreated(date);

        resource = resourceJpaRepository.save(resource);

        project.setResource(resource);
        projectRepository.update(project);

        return resource;
    }

    @Override
    public Resource update(Resource resource) {
        resource.setUpdated(TimeUtil.getCurrentDate());
        return resourceJpaRepository.save(resource);
    }

    @Override
    public Resource getById(Long id) {
        return resourceJpaRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean isExist(Long id) {
        return resourceJpaRepository.existsById(id);
    }

    @Override
    public Boolean isExist(String filename) {
        // FIXME
        return resourceJpaRepository.getResourceIdByFilename(filename) != null;
    }

    @Override
    public Boolean delete(Long id) {
        if (!isExist(id)) {
            return false;
        }

        resourceJpaRepository.deleteById(id);

        return true;
    }

    @Override
    public String getOwnerUsername(String filename) {
        Long resourceId = resourceJpaRepository.getResourceIdByFilename(filename);
        return projectRepository.getProjectOwnerUsernameByResourceId(resourceId);
    }
}
