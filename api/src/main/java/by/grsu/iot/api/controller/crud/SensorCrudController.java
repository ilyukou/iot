package by.grsu.iot.api.controller.crud;

import by.grsu.iot.model.dto.PageWrapper;
import by.grsu.iot.model.dto.sort.RequestSortType;
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

    @GetMapping("{project}")
    public ResponseEntity<PageWrapper<SensorDto>> getSensorPage(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long project,
            @RequestParam(required = false, defaultValue = "${by.grsu.iot.api.controller.crud.sensor.page.size}") Integer size,
            @RequestParam(required = false, defaultValue = "${by.grsu.iot.api.controller.page.startFrom}") Integer page,
            @RequestParam(required = false, defaultValue = "${by.grsu.iot.api.controller.page.sort.type}") RequestSortType sort,
            @RequestParam(required = false, defaultValue = "${by.grsu.iot.api.controller.page.sort.field}") String field
    ) {
        return new ResponseEntity<>(
                sensorCrudService.getPage(project, userDetails.getUsername(), size, page, sort, field),
                HttpStatus.OK);
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
