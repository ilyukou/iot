package by.grsu.iot.api.controller;

import by.grsu.iot.api.model.dto.thing.sensor.SensorValue;
import by.grsu.iot.api.service.SensorValueService;
import by.grsu.iot.api.util.TimeUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.ResponseEntity.badRequest;

@CrossOrigin
@RestController
@RequestMapping("/sensor/value")
public class SensorValueController {

    private final SensorValueService sensorValueService;

    public SensorValueController(SensorValueService sensorValueService) {
        this.sensorValueService = sensorValueService;
    }

    @PostMapping("{token}")
    public ResponseEntity<Void> add(
            @PathVariable String token,
            @RequestBody SensorValue sensorValue
    ) {
        if (sensorValue.getTime() == null) {
            sensorValue.setTime(TimeUtil.getCurrentDate().getTime());
        }

        if (sensorValue.getValue() == null) {
            return badRequest().build();
        }

        sensorValueService.add(token, sensorValue);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("one/{token}")
    public ResponseEntity<SensorValue> getOneValue(
            @PathVariable String token
    ) throws IOException {
        return new ResponseEntity<>(sensorValueService.getOneValue(token), HttpStatus.OK);
    }

    @GetMapping("{token}")
    public ResponseEntity<List<SensorValue>> getByParam(
            @PathVariable String token,
            @RequestParam(required = false) Long from,
            @RequestParam(required = false) Long to
    ) throws IOException {

        if (to == null) {
            to = TimeUtil.getCurrentDate().getTime();
        }

        if (from == null) {
            from = 0L;
        }

        return new ResponseEntity<>(sensorValueService.get(token, from, to), HttpStatus.OK);
    }
}
