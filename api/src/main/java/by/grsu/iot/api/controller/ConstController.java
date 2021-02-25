package by.grsu.iot.api.controller;

import by.grsu.iot.service.interf.ConstService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/const")
public class ConstController {

    private final ConstService constService;

    public ConstController(ConstService constService) {
        this.constService = constService;
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> get() {
        return new ResponseEntity<>(
                constService.getConstFields(),
                HttpStatus.OK);
    }
}
