package by.grsu.iot.api.controller.crud;

import by.grsu.iot.api.dto.project.ProjectDto;
import by.grsu.iot.service.domain.project.ProjectForm;
import by.grsu.iot.service.domain.project.ProjectFormUpdate;
import by.grsu.iot.service.interf.crud.ProjectCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/crud/project")
public class ProjectCrudController {

    private final ProjectCrudService projectCrudService;

    public ProjectCrudController(ProjectCrudService projectCrudService) {
        this.projectCrudService = projectCrudService;
    }

    @PostMapping
    public ResponseEntity<ProjectDto> create(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ProjectForm projectForm
    ) {
        return new ResponseEntity<>(
                new ProjectDto(
                        projectCrudService.create(projectForm, userDetails.getUsername())),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDto> update(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @RequestBody ProjectFormUpdate projectFormUpdate
    ) {
        return new ResponseEntity<>(
                new ProjectDto(projectCrudService.update(id, projectFormUpdate, userDetails.getUsername())),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> get(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(new ProjectDto(projectCrudService.getById(id, userDetails.getUsername())), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id
    ) {
        projectCrudService.delete(id, userDetails.getUsername());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
