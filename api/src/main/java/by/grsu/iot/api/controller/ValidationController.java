package by.grsu.iot.api.controller;

import by.grsu.iot.model.dto.validaation.ObjectValidation;
import by.grsu.iot.service.interf.ValidationService;
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

    private final ValidationService validationService;

    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @GetMapping
    public ResponseEntity<Map<String, ObjectValidation>> getAll() {
        return new ResponseEntity<>(validationService.getValidationRuleForAllRequestEntity(), HttpStatus.OK);
    }
}
