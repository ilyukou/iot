package by.grsu.iot.api.controller.crud;

import by.grsu.iot.model.dto.project.ProjectDto;
import by.grsu.iot.model.dto.project.ProjectForm;
import by.grsu.iot.model.dto.project.ProjectFormUpdate;
import by.grsu.iot.service.interf.crud.ProjectCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasPermission(#id, #this.this.class.name, 'update')")
    @PutMapping("/{id}")
    public ResponseEntity<ProjectDto> update(
            @PathVariable Long id,
            @RequestBody ProjectFormUpdate projectFormUpdate
    ) {
        return new ResponseEntity<>(
                new ProjectDto(projectCrudService.update(id, projectFormUpdate)),
                HttpStatus.OK);
    }

    @PreAuthorize("hasPermission(#id, #this.this.class.name, 'read')")
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> get(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(new ProjectDto(projectCrudService.getById(id)), HttpStatus.OK);
    }

    @PreAuthorize("hasPermission(#id, #this.this.class.name, 'delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        projectCrudService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
