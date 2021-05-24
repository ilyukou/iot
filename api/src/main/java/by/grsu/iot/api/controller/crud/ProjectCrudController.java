package by.grsu.iot.api.controller.crud;

import by.grsu.iot.model.dto.PageWrapper;
import by.grsu.iot.model.dto.project.ProjectDto;
import by.grsu.iot.model.dto.project.ProjectForm;
import by.grsu.iot.model.dto.project.ProjectFormUpdate;
import by.grsu.iot.model.dto.sort.RequestSortType;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.service.interf.crud.ProjectCrudService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
