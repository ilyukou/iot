package by.grsu.iot.service.validation.interf;

import by.grsu.iot.model.sql.Project;
import by.grsu.iot.service.exception.BadRequestException;

/**
 * Service that validate a {@link Project} fields.
 *
 * @author Ilya Ilyukou
 */
public interface ProjectValidationService {

    /**
     * Method to validate {@link Project#getName()}
     *
     * @param name {@link Project#getName()}
     * @throws BadRequestException if field incorrect
     */
    void validateName(String name) throws BadRequestException;

    /**
     * Method to validate {@link Project#getTitle()}
     *
     * @param title {@link Project#getTitle()}
     * @throws BadRequestException if field incorrect
     */
    void validateTitle(String title) throws BadRequestException;
}
