package by.grsu.iot.api.controller;

import by.grsu.iot.service.domain.response.HttpMessageEnum;
import by.grsu.iot.service.domain.response.HttpMessageWrapper;
import by.grsu.iot.service.domain.response.DeviceStateDto;
import by.grsu.iot.service.interf.DeviceStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/deviceState")
public class DeviceStateController {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceStateController.class);

    private final DeviceStateService deviceStateService;

    public DeviceStateController(
            DeviceStateService deviceStateService
    ) {
        this.deviceStateService = deviceStateService;
    }

    @GetMapping("{token}")
    public ResponseEntity<HttpMessageWrapper<DeviceStateDto>> getState(
            @PathVariable String token,
            @RequestParam String state
    ) {
        return getResponseEntity(new DeviceStateDto(deviceStateService.getState(state, token)));
    }

    @PostMapping("{token}")
    public ResponseEntity<HttpMessageWrapper<DeviceStateDto>> setState(
            @PathVariable String token,
            @RequestParam String state
    ) {
        return getResponseEntity(new DeviceStateDto(deviceStateService.setState(state, token)));
    }

    private ResponseEntity<HttpMessageWrapper<DeviceStateDto>> getResponseEntity(DeviceStateDto deviceStateDto){
        if (deviceStateDto != null){
            return new ResponseEntity<>(
                    new HttpMessageWrapper(
                            HttpMessageEnum.info,
                            "OK",
                            deviceStateDto),
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
