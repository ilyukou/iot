package by.grsu.iot.api.controller;

import by.grsu.iot.api.dto.ProjectDto;
import by.grsu.iot.api.dto.ThingWrapper;
import by.grsu.iot.model.api.ProjectForm;
import by.grsu.iot.service.domain.PaginationInfo;
import by.grsu.iot.service.domain.ProjectThing;
import by.grsu.iot.service.exception.ExceptionUtil;
import by.grsu.iot.service.interf.ProjectService;
import by.grsu.iot.service.validation.validator.ProjectFormValidator;
import by.grsu.iot.model.sql.Project;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.badRequest;

/**
 * This controller allows CRUD operation with {@link Project}.
 */
@CrossOrigin
@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectDto> create(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ProjectForm projectForm
    ) {

        return new ResponseEntity<>(
                new ProjectDto(
                        projectService.create(projectForm, userDetails.getUsername())),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDto> update(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @RequestBody ProjectForm projectForm
    ) {

        return new ResponseEntity<>(
                new ProjectDto(projectService.update(id, projectForm, userDetails.getUsername())),
                HttpStatus.OK);
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

    @GetMapping("/pagination")
    public ResponseEntity<PaginationInfo> getProjects(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) String username
    ) {
        if (username == null){
            return new ResponseEntity<>(
                    projectService.getPaginationInfoAboutUserProjects(userDetails.getUsername(), userDetails.getUsername())
                    ,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    projectService.getPaginationInfoAboutUserProjects(username, userDetails.getUsername())
                    ,HttpStatus.OK);
        }
    }

    @GetMapping("/page")
    public ResponseEntity<List<ProjectDto>> getProjects(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) String username,
            @RequestParam Integer count
    ) {
        if(count == 0){
            return badRequest().build();
        }

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

    @GetMapping("/thing/pagination/{project}")
    public ResponseEntity<PaginationInfo> getCountThingPages(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long project
    ){
        return new ResponseEntity<>(
                projectService.getPaginationInfoAboutProjectThing(project, userDetails.getUsername())
//                projectService.getCountThingPages(project, userDetails.getUsername())
                , HttpStatus.OK);
    }

    @GetMapping("/thing/page/{project}")
    public ResponseEntity<List<ThingWrapper>> getThingPage(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long project,
            @RequestParam Integer count
    ){
        return new ResponseEntity<>(
                projectService.getThingPage(project, count, userDetails.getUsername()).stream()
                        .map(ThingWrapper::new)
                        .collect(Collectors.toList())
                , HttpStatus.OK);
    }
}
