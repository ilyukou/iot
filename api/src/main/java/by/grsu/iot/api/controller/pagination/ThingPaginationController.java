package by.grsu.iot.api.controller.pagination;

import by.grsu.iot.model.dto.thing.ThingWrapper;
import by.grsu.iot.model.dto.pagination.PaginationInfo;
import by.grsu.iot.service.interf.pagination.ThingPaginationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/pagination/thing/")
public class ThingPaginationController {

    private final ThingPaginationService thingPaginationService;

    public ThingPaginationController(ThingPaginationService thingPaginationService) {
        this.thingPaginationService = thingPaginationService;
    }

    @GetMapping("{project}")
    public ResponseEntity<PaginationInfo> getPaginationInfo(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long project
    ) {
        return new ResponseEntity<>(
                thingPaginationService.getPaginationInfo(project, userDetails.getUsername())
                , HttpStatus.OK);
    }

    @GetMapping("{project}/{page}")
    public ResponseEntity<List<ThingWrapper>> getThingsFromProjectPage(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long project,
            @PathVariable Integer page
    ) {
        return new ResponseEntity<>(
                thingPaginationService.getThingsFromProjectPage(project, page, userDetails.getUsername()).stream()
                        .map(ThingWrapper::new)
                        .collect(Collectors.toList())
                , HttpStatus.OK);
    }
}
