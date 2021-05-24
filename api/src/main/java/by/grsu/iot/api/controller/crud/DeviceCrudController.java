package by.grsu.iot.api.controller.crud;

import by.grsu.iot.model.dto.PageWrapper;
import by.grsu.iot.model.dto.sort.RequestSortType;
import by.grsu.iot.model.dto.thing.device.DeviceDto;
import by.grsu.iot.model.dto.thing.device.DeviceForm;
import by.grsu.iot.model.dto.thing.device.DeviceFormUpdate;
import by.grsu.iot.model.sql.Device;
import by.grsu.iot.service.interf.crud.DeviceCrudService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/crud/device")
public class DeviceCrudController {

    private final DeviceCrudService deviceCrudService;

    public DeviceCrudController(DeviceCrudService deviceCrudService) {
        this.deviceCrudService = deviceCrudService;
    }

    @PreAuthorize("hasPermission(#project, #this.this.class.name, 'read-page')")
    @GetMapping
    public ResponseEntity<PageWrapper<DeviceDto>> getDevicePage(
            @RequestParam Long project,
            @RequestParam(required = false, defaultValue = "${by.grsu.iot.api.controller.crud.device.page.size}") Integer size,
            @RequestParam(required = false, defaultValue = "${by.grsu.iot.api.controller.page.startFrom}") Integer page,
            @RequestParam(required = false, defaultValue = "${by.grsu.iot.api.controller.page.sort.type}") RequestSortType sort,
            @RequestParam(required = false, defaultValue = "${by.grsu.iot.api.controller.page.sort.field}") String field
    ) {

        Page<Device> pgs = deviceCrudService.getPage(project, size, page, sort, field);

        return new ResponseEntity<>(
                new PageWrapper<>(pgs.stream().map(DeviceDto::new).collect(Collectors.toList()), pgs.hasNext(), pgs.getTotalPages(), pgs.getTotalElements()),
                HttpStatus.OK);
    }

    @PreAuthorize("hasPermission(#deviceForm.project, #this.this.class.name, 'create')")
    @PostMapping
    public ResponseEntity<DeviceDto> create(
            @RequestBody DeviceForm deviceForm
    ) {
        return new ResponseEntity<>(
                new DeviceDto(deviceCrudService.create(deviceForm)),
                HttpStatus.OK);
    }

    @PreAuthorize("hasPermission(#id, #this.this.class.name, 'update')")
    @PutMapping("/{id}")
    public ResponseEntity<DeviceDto> update(
            @PathVariable Long id,
            @RequestBody DeviceFormUpdate deviceFormUpdate
    ) {
        return new ResponseEntity<>(
                new DeviceDto(deviceCrudService.update(id, deviceFormUpdate)),
                HttpStatus.OK);
    }

    @PreAuthorize("hasPermission(#id, #this.this.class.name, 'read')")
    @GetMapping("{id}")
    public ResponseEntity<DeviceDto> get(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(new DeviceDto(deviceCrudService.getById(id)), HttpStatus.OK);
    }

    @PreAuthorize("hasPermission(#id, #this.this.class.name, 'delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        deviceCrudService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
