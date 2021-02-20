package by.grsu.iot.service.validation.validator;

import by.grsu.iot.model.api.ProjectFormDto;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.validation.interf.ProjectValidationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class ProjectFormDtoValidator implements Validator {

    private final ProjectValidationService projectValidationService;

    public ProjectFormDtoValidator(ProjectValidationService projectValidationService) {
        this.projectValidationService = projectValidationService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return ProjectFormDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProjectFormDto obj = (ProjectFormDto) target;

        try {
            projectValidationService.validateName(obj.getName());
            projectValidationService.validateTitle(obj.getTitle());

        } catch (BadRequestException e){
            Object[] arr = {e};
            errors.rejectValue(e.getField(), "400", arr, e.getMessage());
        }
    }
}
