package by.grsu.iot.service.validation;

import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.service.domain.form.Form;
import by.grsu.iot.service.exception.NotAccessForOperationException;
import by.grsu.iot.service.validation.util.DataBinderValidation;
import org.springframework.stereotype.Service;

@Service
public class ProjectValidationImpl implements ProjectValidation {

    private final ProjectRepository projectRepository;

    public ProjectValidationImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void validateCreateProject(String username, Form form) {
        DataBinderValidation.validateParam(form);
    }

    @Override
    public void validateReadProject(String username, Long project) {
        throwExceptionIfUserNotOwnerProject(username, project);
    }

    @Override
    public void validateUpdateProject(String username, Long project, Form form) {
        DataBinderValidation.validateParam(form);
        throwExceptionIfUserNotOwnerProject(username, project);
    }

    @Override
    public void validateDeleteProject(String username, Long project) {
        throwExceptionIfUserNotOwnerProject(username, project);
    }

    private void throwExceptionIfUserNotOwnerProject(String username, Long project) {
        if (!username.equals(projectRepository.getProjectOwnerUsername(project))) {
            throw new NotAccessForOperationException("That user not has such project");
        }
    }
}
