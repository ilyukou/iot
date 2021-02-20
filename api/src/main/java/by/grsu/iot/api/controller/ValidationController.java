package by.grsu.iot.api.controller;

import by.grsu.iot.service.domain.ValidationRule;
import by.grsu.iot.service.interf.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/validation")
public class ValidationController {

    private final ValidationService validationService;

    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @GetMapping("{entity}")
    public ResponseEntity<Map<String, ValidationRule>> getValidationRules(@PathVariable String entity){
        return new ResponseEntity<>(validationService.getRulesForEntity(entity), HttpStatus.OK);
    }
}
