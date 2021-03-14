package by.grsu.iot.service.config;

import by.grsu.iot.service.domain.DataTransferObject;
import by.grsu.iot.service.domain.validaation.Validation;
import by.grsu.iot.service.domain.validaation.ValidationRule;
import by.grsu.iot.service.util.ObjectUtil;
import org.reflections.Reflections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class ValidationConfig implements WebMvcConfigurer {

    @Bean
    public Validation validation() {
        Reflections reflections = new Reflections("by.grsu.iot.service.domain");

        Set<Class<?>> classes = reflections.getSubTypesOf(DataTransferObject.class).stream()
                .peek(aClass -> Arrays.stream(aClass.getFields()).forEach(field -> field.setAccessible(true)))
                .filter(aClass -> ObjectUtil.hasClassAnnotatedField(aClass, by.grsu.iot.service.annotation.Validation.class))
                .collect(Collectors.toSet());

        Map<String, Map<String, ValidationRule>> map = new HashMap<>();

        for (Class<?> clazz : classes) {
            Map<String, ValidationRule> fieldsMap = new HashMap<>();

            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(by.grsu.iot.service.annotation.Validation.class)) {
                    fieldsMap.put(field.getName(),
                            new ValidationRule(field.getAnnotation(by.grsu.iot.service.annotation.Validation.class)));
                }
            }

            map.put(clazz.getSimpleName(), fieldsMap);
        }

        return new Validation(map);
    }
}
