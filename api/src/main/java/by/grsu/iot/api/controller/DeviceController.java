package by.grsu.iot.api.controller;

import by.grsu.iot.api.dto.*;
import by.grsu.iot.model.api.DeviceFormDto;
import by.grsu.iot.service.exception.ExceptionUtil;
import by.grsu.iot.service.interf.DeviceService;
import by.grsu.iot.service.validation.validator.DeviceFormDtoValidator;
import by.grsu.iot.model.sql.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@RequestMapping("/device")
public class DeviceController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final DeviceService deviceService;
    private final DeviceFormDtoValidator deviceFormDtoValidator;

    public DeviceController(DeviceService deviceService, DeviceFormDtoValidator deviceFormDtoValidator) {
        this.deviceService = deviceService;
        this.deviceFormDtoValidator = deviceFormDtoValidator;
    }

    @InitBinder("deviceFormDto")
    protected void initAuthenticationRequestBinder(WebDataBinder binder) {
        binder.setValidator(deviceFormDtoValidator);
    }

    @PostMapping
    public ResponseEntity<DeviceDto> create(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid DeviceFormDto dto, BindingResult result
    ) {

        if (result.hasErrors()) {
            ExceptionUtil.throwException(result);
        }

        Device device = new Device();

        device.setState(dto.getState());
        device.setStates(dto.getStates());
        device.setName(dto.getName());

        return new ResponseEntity<>(
                new DeviceDto(deviceService.create(dto.getProject(), device, userDetails.getUsername())),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceDto> update(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @RequestBody @Valid DeviceFormDto dto, BindingResult result
    ) {

        if (result.hasErrors()) {
            ExceptionUtil.throwException(result);
        }

        Device device = new Device();

        device.setState(dto.getState());
        device.setStates(dto.getStates());
        device.setName(dto.getName());

        return new ResponseEntity<>(
                new DeviceDto(deviceService.update(id, device, userDetails.getUsername())),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDto> get(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(new DeviceDto(deviceService.getById(id, userDetails.getUsername())), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id
    ) {
        deviceService.deleteById(id, userDetails.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
