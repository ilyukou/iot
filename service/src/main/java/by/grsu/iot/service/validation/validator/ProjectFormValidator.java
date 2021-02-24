package by.grsu.iot.service.validation.validator;

import by.grsu.iot.model.api.ProjectForm;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.validation.interf.ProjectValidationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class ProjectFormValidator implements Validator {

    private final ProjectValidationService projectValidationService;

    public ProjectFormValidator(ProjectValidationService projectValidationService) {
        this.projectValidationService = projectValidationService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ProjectForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        try {
            if (target == null){
                throw new BadRequestException("Project is null");
            }

            ProjectForm obj = (ProjectForm) target;

            if (obj.getName() == null){
                throw new BadRequestException("name", "Name is null");
            }

            if (obj.getTitle() == null){
                throw new BadRequestException("title", "Title is null");
            }

            projectValidationService.validateName(obj.getName());
            projectValidationService.validateTitle(obj.getTitle());

        } catch (BadRequestException e){
            Object[] arr = {e};
            errors.rejectValue(e.getField(), "400", arr, e.getMessage());
        }
    }
}
