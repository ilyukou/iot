package by.grsu.iot.api.controller.crud;

import by.grsu.iot.model.dto.thing.device.DeviceDto;
import by.grsu.iot.model.dto.thing.device.DeviceForm;
import by.grsu.iot.model.dto.thing.device.DeviceFormUpdate;
import by.grsu.iot.service.interf.crud.DeviceCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/crud/device")
public class DeviceCrudController {

    private final DeviceCrudService deviceCrudService;

    public DeviceCrudController(DeviceCrudService deviceCrudService) {
        this.deviceCrudService = deviceCrudService;
    }

    @PreAuthorize("hasPermission(#deviceForm.project, #this.this.class.name, 'create')")
    @PostMapping
    public ResponseEntity<DeviceDto> create(
            @RequestBody DeviceForm deviceForm
    ) {
        return new ResponseEntity<>(
                new DeviceDto(deviceCrudService.create(deviceForm)),
                HttpStatus.OK);
    }

    @PreAuthorize("hasPermission(#id, #this.this.class.name, 'update')")
    @PutMapping("/{id}")
    public ResponseEntity<DeviceDto> update(
            @PathVariable Long id,
            @RequestBody DeviceFormUpdate deviceFormUpdate
    ) {
        return new ResponseEntity<>(
                new DeviceDto(deviceCrudService.update(id, deviceFormUpdate)),
                HttpStatus.OK);
    }

    @PreAuthorize("hasPermission(#id, #this.this.class.name, 'read')")
    @GetMapping("{id}")
    public ResponseEntity<DeviceDto> get(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(new DeviceDto(deviceCrudService.getById(id)), HttpStatus.OK);
    }

    @PreAuthorize("hasPermission(#id, #this.this.class.name, 'delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        deviceCrudService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
