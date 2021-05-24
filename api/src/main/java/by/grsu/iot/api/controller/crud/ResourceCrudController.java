package by.grsu.iot.api.controller.crud;

import by.grsu.iot.model.dto.resource.ResourceDto;
import by.grsu.iot.service.interf.crud.ResourceCrudService;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/crud/resource")
public class ResourceCrudController {

    private final ResourceCrudService resourceCrudService;

    public ResourceCrudController(ResourceCrudService resourceCrudService) {
        this.resourceCrudService = resourceCrudService;
    }

    @PostMapping("{project}")
    public ResponseEntity<ResourceDto> create(
            @PathVariable Long project,
            HttpServletRequest request
    ) throws IOException {
        return new ResponseEntity<>(new ResourceDto(resourceCrudService.create(project, request)), HttpStatus.OK);
    }

    @GetMapping(value = "{resource}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String resource
    ) throws IOException {
        return IOUtils.toByteArray(resourceCrudService.get(resource, userDetails.getUsername()));
    }
}
