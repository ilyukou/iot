package by.grsu.iot.api.controller;

import by.grsu.iot.model.domain.DeviceState;
import by.grsu.iot.model.sql.Device;
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


    private static final Long timeOut = 20000l;

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
            @PathVariable String token,
            @RequestParam String state
    ) {
//        LOGGER.info("Get State Controller - " + Thread.currentThread().getName());
//        LOGGER.info("New device " + state + " " + token);

        DeferredResult<ResponseEntity<HttpMessageWrapper<DeviceStateDto>>>
                deferredResult = new DeferredResult<>(timeOut, getDefaultDeferredResult());

        deferredResult.onTimeout(() -> {
            deviceStateService.removeDevice(token);

            Device device = deviceCrudService.getByToken(token);

            if (!device.getState().equals(state)){
                deferredResult.setResult(getDeferredResult(
                        new DeviceState(device.getToken(), device.getState())));
            }
        });

        ForkJoinPool.commonPool().submit(() -> {
//            LOGGER.info("Get State - " + Thread.currentThread().getName());

            DeviceState deviceState = deviceStateService.getState(state, token);

            deferredResult.setResult(getDeferredResult(deviceState));
        });

        return deferredResult;
    }

    @PostMapping("{token}")
    public DeferredResult<ResponseEntity<HttpMessageWrapper<DeviceStateDto>>> setState(
            @PathVariable String token,
            @RequestParam String state
    ) {
//        LOGGER.info("Set State Controller - " + Thread.currentThread().getName());
//        LOGGER.info("New request " + state + " " + token);

        DeferredResult<ResponseEntity<HttpMessageWrapper<DeviceStateDto>>>
                deferredResult = new DeferredResult<>(timeOut, getDefaultDeferredResult());

        deferredResult.onTimeout(() -> {
            deviceStateService.removeRequest(token);
            Device device = deviceCrudService.getByToken(token);

            if (device.getState().equals(state)){
                deferredResult.setResult(getDeferredResult(
                        new DeviceState(device.getToken(), device.getState())));
            }
        });

        ForkJoinPool.commonPool().submit(() -> {
//            LOGGER.info("Set State - " + Thread.currentThread().getName());

            DeviceState deviceState = deviceStateService.setState(state, token);

            deferredResult.setResult(getDeferredResult(deviceState));
        });

        return deferredResult;
    }

    private ResponseEntity<HttpMessageWrapper<DeviceStateDto>> getDefaultDeferredResult(){
        return new ResponseEntity<>(
                new HttpMessageWrapper(
                        HttpMessageEnum.info,
                        "Time Out",
                        null),
                HttpStatus.NO_CONTENT);
    }

    private ResponseEntity<HttpMessageWrapper<DeviceStateDto>> getDeferredResult(DeviceState deviceState){
        return new ResponseEntity<>(
                new HttpMessageWrapper(
                        HttpMessageEnum.info,
                        "OK",
                        new DeviceStateDto(deviceState)),
                HttpStatus.OK);
    }

}
