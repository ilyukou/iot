package by.grsu.iot.api.config;

import by.grsu.iot.api.dto.validation.ValidationRule;
import by.grsu.iot.api.validation.ValidationStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@PropertySource("classpath:application-validation.properties")
public class ValidationConfig {

    private final Environment env;

    public ValidationConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public ValidationStorage validationStorage() {
        return new ValidationStorage(
                getValidation("user", Arrays.asList("password", "username")),
                getValidation("project", Arrays.asList("name", "title")),
                getValidation("device", Arrays.asList("name", "state"))
        );
    }

    private Map<String, ValidationRule> getValidation(String entity, List<String> fields){
        Map<String, ValidationRule> validation = new HashMap<>();

        for (String field: fields){
            validation.put(field, getValidationRuleForField(entity, field));
        }

        return validation;
    }

    private ValidationRule getValidationRuleForField(String entity, String field) {
        return new ValidationRule(
                Integer.parseInt(env.getProperty((entity + "." + field + ".min"))),
                Integer.parseInt(env.getProperty((entity + "." + field + ".max"))),
                Boolean.parseBoolean(env.getProperty((entity + "." + field + ".space")))
        );

    }
}
