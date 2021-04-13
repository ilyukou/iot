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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/crud/resource")
public class ResourceCrudController {

    private final ResourceCrudService resourceCrudService;

    public ResourceCrudController(ResourceCrudService resourceCrudService) {
        this.resourceCrudService = resourceCrudService;
    }

    @PostMapping
    public ResponseEntity<ResourceDto> create(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam Long project,
            @RequestParam(value = "file") MultipartFile multipartFile
    ) {

        if (multipartFile == null || multipartFile.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String[] file = multipartFile.getOriginalFilename().split("\\.");
        if (file.length != 2){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!resourceCrudService.support(file[1])){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(
                new ResourceDto(
                        resourceCrudService.create(multipartFile, project, userDetails.getUsername())),
                HttpStatus.OK);
    }

    @GetMapping(value = "{resource}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String resource
    ) throws IOException {
        return IOUtils.toByteArray(resourceCrudService.get(resource, userDetails.getUsername()));
    }
}
