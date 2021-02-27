package by.grsu.iot.api.controller;

import by.grsu.iot.service.domain.DeviceState;
import by.grsu.iot.api.dto.HttpMessageEnum;
import by.grsu.iot.api.dto.HttpMessageWrapper;
import by.grsu.iot.service.interf.DeviceStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/deviceState")
public class DeviceStateController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final DeviceStateService deviceStateService;

    public DeviceStateController(
            DeviceStateService deviceStateService
    ) {
        this.deviceStateService = deviceStateService;
    }

    @GetMapping("{token}")
    public ResponseEntity<HttpMessageWrapper<DeviceState>> getState(
            @PathVariable String token,
            @RequestParam String state
    ) {
        return getResponseEntity(deviceStateService.getState(state, token));
    }

    @PostMapping("{token}")
    public ResponseEntity<HttpMessageWrapper<DeviceState>> setState(
            @PathVariable String token,
            @RequestParam String state
    ) {
        return getResponseEntity(deviceStateService.setState(state, token));
    }

    private ResponseEntity<HttpMessageWrapper<DeviceState>> getResponseEntity(DeviceState deviceState){
        if (deviceState != null){
            return new ResponseEntity<>(
                    new HttpMessageWrapper(
                            HttpMessageEnum.info,
                            "OK",
                            deviceState),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    new HttpMessageWrapper(
                            HttpMessageEnum.info,
                            "Time Out",
                            null),
                    HttpStatus.NO_CONTENT);
        }
    }
}
