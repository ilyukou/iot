package by.grsu.iot.api.controller.crud;

import by.grsu.iot.api.model.dto.PageWrapper;
import by.grsu.iot.api.model.dto.project.ProjectDto;
import by.grsu.iot.api.model.dto.project.ProjectForm;
import by.grsu.iot.api.model.dto.project.ProjectFormUpdate;
import by.grsu.iot.api.model.dto.sort.RequestSortType;
import by.grsu.iot.api.model.sql.Project;
import by.grsu.iot.api.service.crud.project.ProjectCrudService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/crud/project")
public class ProjectCrudController {

    private final ProjectCrudService projectCrudService;

    public ProjectCrudController(ProjectCrudService projectCrudService) {
        this.projectCrudService = projectCrudService;
    }

    @GetMapping
    public ResponseEntity<PageWrapper<ProjectDto>> getProjectPage(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false, defaultValue = "${by.grsu.iot.api.controller.crud.project.page.size}") Integer size,
            @RequestParam(required = false, defaultValue = "${by.grsu.iot.api.controller.page.startFrom}") Integer page,
            @RequestParam(required = false, defaultValue = "${by.grsu.iot.api.controller.page.sort.type}") RequestSortType sort,
            @RequestParam(required = false, defaultValue = "${by.grsu.iot.api.controller.page.sort.field}") String field
    ) {

        Page<Project> p = projectCrudService.getPage(userDetails.getUsername(), size, page, sort, field);

        return new ResponseEntity<>(
                new PageWrapper<>(p.stream().map(ProjectDto::new).collect(Collectors.toList()), p.hasNext(), p.getTotalPages(), p.getTotalElements()),
                HttpStatus.OK);
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
