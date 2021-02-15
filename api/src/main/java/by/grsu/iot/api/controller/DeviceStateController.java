package by.grsu.iot.api.controller;

import by.grsu.iot.api.dto.DeviceState;
import by.grsu.iot.api.dto.HttpMessageEnum;
import by.grsu.iot.api.dto.HttpMessageWrapper;
import by.grsu.iot.api.service.interf.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.ForkJoinPool;

@CrossOrigin
@RestController
@RequestMapping("/deviceState")
public class DeviceStateController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    @Value("${iot.http.long-polling.timeout.device-state}")
    private long TIME_OUT_IN_MILLIS;

    private final DeviceService deviceService;

    public DeviceStateController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("{token}")
    public DeferredResult<ResponseEntity<HttpMessageWrapper<DeviceState>>> getState(
            @PathVariable String token,
            @RequestParam String state
    ) {

        // Default time out response
        DeferredResult<ResponseEntity<HttpMessageWrapper<DeviceState>>> deferredResult =
                new DeferredResult<>(TIME_OUT_IN_MILLIS,
                        new HttpMessageWrapper(
                                HttpMessageEnum.info,
                                "Time Out",
                                null)
                );

        deferredResult.onCompletion(() -> LOG.info("Processing complete"));

        ForkJoinPool.commonPool().submit(() -> {

            // Result
            ResponseEntity<HttpMessageWrapper<DeviceState>> responseEntity = new ResponseEntity<>(
                    new HttpMessageWrapper<>(
                            HttpMessageEnum.ok,
                            "ok",
                            deviceService.getStateWhenDeviceStateNotEqualState(token, state)
                    ),
                    HttpStatus.OK
            );

            deferredResult.setResult(responseEntity);

        });
        return deferredResult;
    }

    @PostMapping("{token}")
    public DeferredResult<ResponseEntity<HttpMessageWrapper<DeviceState>>> setState(
            @PathVariable String token,
            @RequestParam String state
    ) {
        // Default time out response
        DeferredResult<ResponseEntity<HttpMessageWrapper<DeviceState>>> deferredResult =
                new DeferredResult<>(TIME_OUT_IN_MILLIS,
                        new HttpMessageWrapper(
                                HttpMessageEnum.info,
                                "Time Out",
                                null)
                );

        deferredResult.onCompletion(() -> LOG.info("Processing complete"));
        deferredResult.onTimeout(() -> {
            LOG.info("Processing failed");
            deviceService.deleteDeviceStateChangeRequests(token);
        });

        // Result
        ResponseEntity<HttpMessageWrapper<DeviceState>> responseEntity = new ResponseEntity<>(
                new HttpMessageWrapper<>(
                        HttpMessageEnum.ok,
                        "ok",
                        deviceService.setState(token, state)
                ),
                HttpStatus.OK
        );

        deferredResult.setResult(responseEntity);

        return deferredResult;
    }
}
