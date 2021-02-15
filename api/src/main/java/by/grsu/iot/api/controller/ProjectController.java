package by.grsu.iot.api.controller;

import by.grsu.iot.api.dto.ProjectDto;
import by.grsu.iot.api.dto.ProjectFormDto;
import by.grsu.iot.api.dto.ProjectThing;
import by.grsu.iot.api.exception.ExceptionUtil;
import by.grsu.iot.api.service.interf.ProjectService;
import by.grsu.iot.api.validation.validator.ProjectFormDtoValidator;
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
    private final ProjectFormDtoValidator projectFormDtoValidator;

    public ProjectController(ProjectService projectService, ProjectFormDtoValidator projectFormDtoValidator) {
        this.projectService = projectService;
        this.projectFormDtoValidator = projectFormDtoValidator;
    }

    @InitBinder("projectFormDto")
    protected void initAuthenticationRequestBinder(WebDataBinder binder) {
        binder.setValidator(projectFormDtoValidator);
    }

    @PostMapping
    public ResponseEntity<ProjectDto> create(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid ProjectFormDto projectDto, BindingResult result) {

        if (result.hasErrors()) {
            ExceptionUtil.throwException(result);
        }

        return new ResponseEntity<>(
                new ProjectDto(
                        projectService.create(projectDto.getName(), userDetails.getUsername(),projectDto.getTitle())),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDto> update(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @RequestBody @Valid ProjectFormDto projectDto, BindingResult result
    ) {

        if (result.hasErrors()) {
            ExceptionUtil.throwException(result);
        }

        return new ResponseEntity<>(
                new ProjectDto(projectService.update(id, projectDto.getName(), projectDto.getTitle(),
                                userDetails.getUsername())),
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

    @GetMapping("/page/count")
    public ResponseEntity<Integer> getProjects(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) String username
    ) {
        return new ResponseEntity<>(
                projectService.getCountOfProjectPage(username, userDetails.getUsername())
                ,HttpStatus.OK);
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


    @GetMapping("/thing/{id}")
    public ResponseEntity<List<ProjectThing>> getProjectThing(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id
    ){
        return new ResponseEntity<>(
                projectService.getThings(id, userDetails.getUsername()), HttpStatus.OK);
    }
}
