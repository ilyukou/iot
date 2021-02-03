package by.grsu.iot.api.controller;

import by.grsu.iot.api.dto.*;
import by.grsu.iot.api.service.interf.DeviceService;
import by.grsu.iot.api.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.ForkJoinPool;

@CrossOrigin
@RestController
@RequestMapping("/device")
public class DeviceController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private long TIME_OUT_IN_MILLIS = 30000;

    private final DeviceService deviceService;
    private final ValidationUtil validationUtil;

    public DeviceController(DeviceService deviceService, ValidationUtil validationUtil) {
        this.deviceService = deviceService;
        this.validationUtil = validationUtil;
    }

    @PostMapping
    public ResponseEntity<Long> create(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody DeviceFormDto dto
    ) {
        validationUtil.isUnValidNameForSensor(dto.getName());

        return new ResponseEntity<>(
                new DeviceDto(deviceService.create(dto.getProject(), dto.getName(), userDetails.getUsername())).getId(),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @RequestBody DeviceFormDto dto
    ) {
        validationUtil.isUnValidNameForSensor(dto.getName());

        deviceService.update(id, dto.getName(), dto.getState(), userDetails.getUsername());

        return new ResponseEntity<>(HttpStatus.OK);
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

    @GetMapping("/state/{token}")
    public DeferredResult<ResponseEntity<HttpMessageWrapper<DeviceState>>> getCurrentState(
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
}
