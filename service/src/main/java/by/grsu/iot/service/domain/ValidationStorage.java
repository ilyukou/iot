package by.grsu.iot.service.domain;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * In-memory storage for validation rules for application entities
 */
@Component
public class ValidationStorage {

    private final Map<String, ValidationRule> user;
    private final Map<String, ValidationRule> project;
    private final Map<String, ValidationRule> device;

    public ValidationStorage(
            Map<String, ValidationRule> user,
            Map<String, ValidationRule> project,
            Map<String, ValidationRule> device
    ) {
        this.user = user;
        this.project = project;
        this.device = device;
    }

    public Map<String, ValidationRule> getUser() {
        return user;
    }

    public Map<String, ValidationRule> getProject() {
        return project;
    }

    public Map<String, ValidationRule> getDevice() {
        return device;
    }
}
