package by.grsu.iot.service.validation;

import by.grsu.iot.service.domain.form.Form;

public interface ProjectValidation {
    void validateCreateProject(String username, Form form);

    void validateReadProject(String username, Long project);

    void validateUpdateProject(String username, Long project, Form form);

    void validateDeleteProject(String username, Long project);
}
