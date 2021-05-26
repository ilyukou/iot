package by.grsu.iot.api.controller;

import by.grsu.iot.api.model.dto.HttpMessageEnum;
import by.grsu.iot.api.model.dto.HttpMessageWrapper;
import by.grsu.iot.api.model.dto.exception.ApplicationExceptionDto;
import by.grsu.iot.api.model.dto.thing.device.DeviceState;
import by.grsu.iot.api.model.exception.ConflictException;
import by.grsu.iot.api.service.DeviceStateService;
import by.grsu.iot.api.service.crud.DeviceCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Date;

import static io.vavr.API.*;

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
    public DeferredResult<ResponseEntity<HttpMessageWrapper<DeviceState>>> getState(
            @PathVariable String token
    ) {
        DeferredResult<ResponseEntity<HttpMessageWrapper<DeviceState>>>
                deferredResult = new DeferredResult<>(deviceStateService.getDeviceWaitTime(),
                getDeferredResult(null, TIME_OUT_MESSAGE, HttpStatus.NO_CONTENT));

        deferredResult.onTimeout(() -> {
            deviceStateService.removeDevice(token);

            deferredResult.setResult(getDeferredResult(
                    new DeviceState(deviceCrudService.getDeviceState(token)), TIME_OUT_MESSAGE, HttpStatus.OK));
        });

        new Thread() {
            public void run() {
                try {
                    LOGGER.trace("[GET] " + token);

                    DeviceState deviceState = deviceStateService.getState(token);

                    deferredResult.setResult(getDeferredResult(deviceState, OK_MESSAGE, HttpStatus.OK));
                } catch (Exception e) {
                    if (!(e instanceof ConflictException)) {
                        deviceStateService.removeDevice(token);
                    }
                    deferredResult.setErrorResult(getExceptionResponse(e));
                }
            }
        }.start();

        return deferredResult;
    }

    @PostMapping("{token}")
    public DeferredResult<ResponseEntity<HttpMessageWrapper<DeviceState>>> setState(
            @PathVariable String token,
            @RequestParam String state
    ) {
        DeferredResult<ResponseEntity<HttpMessageWrapper<DeviceState>>>
                deferredResult = new DeferredResult<>(deviceStateService.getRequestWaitTime(),
                getDeferredResult(null, TIME_OUT_MESSAGE, HttpStatus.OK));

        deferredResult.onTimeout(() -> {
            deviceStateService.removeRequest(token);

            deferredResult.setResult(
                    getDeferredResult(null, TIME_OUT_MESSAGE, HttpStatus.NO_CONTENT));
        });

        new Thread() {
            public void run() {
                try {
                    LOGGER.trace("[SET] " + token + " " + state);

                    DeviceState deviceState = deviceStateService.setState(state, token);

                    deferredResult.setResult(getDeferredResult(deviceState, OK_MESSAGE, HttpStatus.OK));
                } catch (Exception e) {
                    deviceStateService.removeRequest(token);
                    deferredResult.setErrorResult(getExceptionResponse(e));
                }
            }
        }.start();

        return deferredResult;
    }

    private ResponseEntity<HttpMessageWrapper<DeviceState>> getDeferredResult(
            DeviceState deviceState, String message, HttpStatus httpStatus
    ) {
        return new ResponseEntity<>(
                new HttpMessageWrapper(
                        HttpMessageEnum.info,
                        message,
                        deviceState),
                httpStatus);
    }

    private ResponseEntity<ApplicationExceptionDto> getExceptionResponse(Exception e) {
        return Match(true).of(
                Case($(e instanceof ConflictException),
                        new ResponseEntity<>(new ApplicationExceptionDto(new Date(), e.getMessage()), HttpStatus.CONFLICT)),
                Case($(true),
                        new ResponseEntity<>(new ApplicationExceptionDto(new Date(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR)));
    }
}
