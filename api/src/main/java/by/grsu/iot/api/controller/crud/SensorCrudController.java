package by.grsu.iot.api.controller.crud;

import by.grsu.iot.model.dto.thing.sensor.SensorDto;
import by.grsu.iot.model.dto.thing.sensor.SensorForm;
import by.grsu.iot.model.dto.thing.sensor.SensorFormUpdate;
import by.grsu.iot.service.interf.crud.SensorCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/crud/sensor")
public class SensorCrudController {

    private static final Logger LOG = LoggerFactory.getLogger(SensorCrudController.class);

    private final SensorCrudService sensorCrudService;

    public SensorCrudController(SensorCrudService sensorCrudService) {
        this.sensorCrudService = sensorCrudService;
    }

    @PostMapping
    public ResponseEntity<SensorDto> create(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody SensorForm sensorForm
    ) {
        return new ResponseEntity<>(
                new SensorDto(sensorCrudService.create(sensorForm, userDetails.getUsername())),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SensorDto> update(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @RequestBody SensorFormUpdate sensorFormUpdate
    ) {
        return new ResponseEntity<>(
                new SensorDto(sensorCrudService.update(id, sensorFormUpdate, userDetails.getUsername())),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorDto> get(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(new SensorDto(sensorCrudService.getById(id, userDetails.getUsername())), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id
    ) {
        sensorCrudService.delete(id, userDetails.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
