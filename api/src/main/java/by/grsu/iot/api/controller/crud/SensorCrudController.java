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
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasPermission(#project, #this.this.class.name, 'read-page')")
    @GetMapping
    public ResponseEntity<PageWrapper<SensorDto>> getSensorPage(
            @RequestParam Long project,
            @RequestParam(required = false, defaultValue = "${by.grsu.iot.api.controller.crud.sensor.page.size}") Integer size,
            @RequestParam(required = false, defaultValue = "${by.grsu.iot.api.controller.page.startFrom}") Integer page,
            @RequestParam(required = false, defaultValue = "${by.grsu.iot.api.controller.page.sort.type}") RequestSortType sort,
            @RequestParam(required = false, defaultValue = "${by.grsu.iot.api.controller.page.sort.field}") String field
    ) {
        return new ResponseEntity<>(
                sensorCrudService.getPage(project, size, page, sort, field),
                HttpStatus.OK);
    }

    @PreAuthorize("hasPermission(#sensorForm.project, #this.this.class.name, 'create')")
    @PostMapping
    public ResponseEntity<SensorDto> create(
            @RequestBody SensorForm sensorForm
    ) {
        return new ResponseEntity<>(
                new SensorDto(sensorCrudService.create(sensorForm)),
                HttpStatus.OK);
    }

    @PreAuthorize("hasPermission(#id, #this.this.class.name, 'update')")
    @PutMapping("/{id}")
    public ResponseEntity<SensorDto> update(
            @PathVariable Long id,
            @RequestBody SensorFormUpdate sensorFormUpdate
    ) {
        return new ResponseEntity<>(
                new SensorDto(sensorCrudService.update(id, sensorFormUpdate)),
                HttpStatus.OK);
    }

    @PreAuthorize("hasPermission(#id, #this.this.class.name, 'read')")
    @GetMapping("/{id}")
    public ResponseEntity<SensorDto> get(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(new SensorDto(sensorCrudService.getById(id)), HttpStatus.OK);
    }

    @PreAuthorize("hasPermission(#id, #this.this.class.name, 'delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        sensorCrudService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
