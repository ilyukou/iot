package by.grsu.iot.service.interf.pagination;

import by.grsu.iot.model.sql.Project;
import by.grsu.iot.service.domain.PaginationInfo;

import java.util.List;

public interface ProjectPaginationService {
    PaginationInfo getPaginationInfo(String username, String username1);

    List<Project> getProjectsFromPage(Integer count, String username);
}
