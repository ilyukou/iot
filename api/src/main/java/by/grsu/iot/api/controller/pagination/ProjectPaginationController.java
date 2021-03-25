package by.grsu.iot.api.controller.pagination;

import by.grsu.iot.model.dto.pagination.PaginationInfo;
import by.grsu.iot.model.dto.project.ProjectDto;
import by.grsu.iot.service.interf.pagination.ProjectPaginationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.badRequest;

@CrossOrigin
@RestController
@RequestMapping("/pagination/project")
public class ProjectPaginationController {

    private final ProjectPaginationService projectPaginationService;

    public ProjectPaginationController(ProjectPaginationService projectPaginationService) {
        this.projectPaginationService = projectPaginationService;
    }

    @GetMapping
    public ResponseEntity<PaginationInfo> getPaginationInfo(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) String username
    ) {
        if (username == null) {
            username = userDetails.getUsername();
        }

        return new ResponseEntity<>(
                projectPaginationService.getPaginationInfo(username, userDetails.getUsername())
                , HttpStatus.OK);
    }

    @GetMapping("/{page}")
    public ResponseEntity<List<ProjectDto>> getProjectsFromPage(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) String username,
            @PathVariable Integer page
    ) {
        if (page == 0) {
            return badRequest().build();
        }

        if (username == null) {
            username = userDetails.getUsername();
        }

        return new ResponseEntity<>(
                projectPaginationService.getProjectsFromPage(page, username, userDetails.getUsername()).stream()
                        .map(ProjectDto::new)
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }
}
