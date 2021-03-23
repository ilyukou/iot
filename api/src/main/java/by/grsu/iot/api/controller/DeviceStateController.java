package by.grsu.iot.api.controller;

import by.grsu.iot.model.domain.DeviceState;
import by.grsu.iot.service.domain.response.DeviceStateDto;
import by.grsu.iot.service.domain.response.HttpMessageEnum;
import by.grsu.iot.service.domain.response.HttpMessageWrapper;
import by.grsu.iot.service.interf.DeviceStateService;
import by.grsu.iot.service.interf.crud.DeviceCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.ForkJoinPool;

@CrossOrigin
@RestController
@RequestMapping("/deviceState")
public class DeviceStateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceStateController.class);

    private static final String TIME_OUT_MESSAGE = "Time out";
    private static final String OK_MESSAGE = "OK";

    private final DeviceStateService deviceStateService;
    private final DeviceCrudService deviceCrudService;


    public DeviceStateController(
            DeviceStateService deviceStateService,
            DeviceCrudService deviceCrudService) {
        this.deviceStateService = deviceStateService;
        this.deviceCrudService = deviceCrudService;
    }

    @GetMapping("{token}")
    public DeferredResult<ResponseEntity<HttpMessageWrapper<DeviceStateDto>>> getState(
            @PathVariable String token
    ) {
        DeferredResult<ResponseEntity<HttpMessageWrapper<DeviceStateDto>>>
                deferredResult = new DeferredResult<>(deviceStateService.getDeviceWaitTime(),
                getDeferredResult(null, TIME_OUT_MESSAGE, HttpStatus.NO_CONTENT));

        deferredResult.onTimeout(() -> {
            deviceStateService.removeDevice(token);

            deferredResult.setResult(getDeferredResult(
                    new DeviceStateDto(deviceCrudService.getDeviceState(token)), TIME_OUT_MESSAGE, HttpStatus.OK));
        });

        ForkJoinPool.commonPool().submit(() -> {
            LOGGER.trace("[GET] " + token);

            DeviceState deviceState = deviceStateService.getState(token);

            deferredResult.setResult(getDeferredResult(new DeviceStateDto(deviceState), OK_MESSAGE, HttpStatus.OK));
        });

        return deferredResult;
    }

    @PostMapping("{token}")
    public DeferredResult<ResponseEntity<HttpMessageWrapper<DeviceStateDto>>> setState(
            @PathVariable String token,
            @RequestParam String state
    ) {
        DeferredResult<ResponseEntity<HttpMessageWrapper<DeviceStateDto>>>
                deferredResult = new DeferredResult<>(deviceStateService.getRequestWaitTime(),
                getDeferredResult(null, TIME_OUT_MESSAGE, HttpStatus.OK));

        deferredResult.onTimeout(() -> {
            deferredResult.setResult(
                    getDeferredResult(null, TIME_OUT_MESSAGE, HttpStatus.NO_CONTENT));
        });

        ForkJoinPool.commonPool().submit(() -> {
            LOGGER.trace("[SET] " + token + " " + state);

            DeviceState deviceState = deviceStateService.setState(state, token);

            deferredResult.setResult(getDeferredResult(
                    new DeviceStateDto(deviceState.getState()), OK_MESSAGE, HttpStatus.OK));
        });

        return deferredResult;
    }

    private ResponseEntity<HttpMessageWrapper<DeviceStateDto>> getDeferredResult(
            DeviceStateDto deviceStateDto, String message, HttpStatus httpStatus
    ) {
        return new ResponseEntity<>(
                new HttpMessageWrapper(
                        HttpMessageEnum.info,
                        message,
                        deviceStateDto),
                httpStatus);
    }
}
