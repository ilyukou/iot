package by.grsu.iot.api.controller;

import by.grsu.iot.api.dto.ProjectDto;
import by.grsu.iot.api.dto.ProjectFormDto;
import by.grsu.iot.api.service.interf.ProjectService;
import by.grsu.iot.api.util.ValidationUtil;
import by.grsu.iot.model.sql.Project;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This controller allows CRUD operation with {@link Project}.
 */
@CrossOrigin
@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;
    private final ValidationUtil validationUtil;

    public ProjectController(ProjectService projectService, ValidationUtil validationUtil) {
        this.projectService = projectService;
        this.validationUtil = validationUtil;
    }

    @PostMapping
    public ResponseEntity<Long> create(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ProjectFormDto projectDto) {

        validationUtil.isUnValidNameForProject(projectDto.getName());

        return new ResponseEntity<>(projectService.create(projectDto.getName(), userDetails.getUsername(),
                projectDto.getTitle()).getId(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @RequestBody ProjectFormDto projectDto
    ) {
        validationUtil.isUnValidNameForProject(projectDto.getName());

        projectService.update(id, projectDto.getName(), userDetails.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> get(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(new ProjectDto(projectService.getById(id, userDetails.getUsername())), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id
    ) {
        projectService.deleteById(id, userDetails.getUsername());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<List<ProjectDto>> getProjects(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) String username,
            @RequestParam Integer count
    ) {
        if(username != null){
            return new ResponseEntity<>(
                    projectService.getProjectsFromPage(count, username).stream()
                            .map(ProjectDto::new)
                            .collect(Collectors.toList()),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    projectService.getProjectsFromPage(count, userDetails.getUsername()).stream()
                            .map(ProjectDto::new)
                            .collect(Collectors.toList()),
                    HttpStatus.OK);
        }
    }
}
