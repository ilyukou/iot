package by.grsu.iot.api.controller;

import by.grsu.iot.service.domain.validaation.Validation;
import by.grsu.iot.service.domain.validaation.ValidationRule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/validation")
public class ValidationController {

    private final Validation validation;

    public ValidationController(Validation validation) {
        this.validation = validation;
    }

    @GetMapping
    public ResponseEntity<Map<String, Map<String, ValidationRule>>> getAll() {
        return new ResponseEntity<>(validation.getValidationRules(), HttpStatus.OK);
    }
}
