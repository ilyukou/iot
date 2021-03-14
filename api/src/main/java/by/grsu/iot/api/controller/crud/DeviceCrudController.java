package by.grsu.iot.api.controller.crud;

import by.grsu.iot.model.dto.DeviceDto;
import by.grsu.iot.service.domain.request.device.DeviceForm;
import by.grsu.iot.service.domain.request.device.DeviceFormUpdate;
import by.grsu.iot.service.interf.crud.DeviceCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/crud/device")
public class DeviceCrudController {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceCrudController.class);

    private final DeviceCrudService deviceCrudService;

    public DeviceCrudController(DeviceCrudService deviceCrudService) {
        this.deviceCrudService = deviceCrudService;
    }

    @PostMapping
    public ResponseEntity<DeviceDto> create(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody DeviceForm deviceForm
    ) {
        return new ResponseEntity<>(
                new DeviceDto(deviceCrudService.create(deviceForm, userDetails.getUsername())),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceDto> update(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @RequestBody DeviceFormUpdate deviceFormUpdate
    ) {
        return new ResponseEntity<>(
                new DeviceDto(deviceCrudService.update(id, deviceFormUpdate, userDetails.getUsername())),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDto> get(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(new DeviceDto(deviceCrudService.getById(id, userDetails.getUsername())), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id
    ) {
        deviceCrudService.delete(id, userDetails.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
