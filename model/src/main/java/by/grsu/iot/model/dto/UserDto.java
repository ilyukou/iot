package by.grsu.iot.model.dto;

import by.grsu.iot.model.sql.BaseEntity;
import by.grsu.iot.model.sql.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO for {@link User}
 */
public class UserDto {

    private Long id;

    private String username;

    private List<Long> projects;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.projects = user.getProjects()
                .stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Long> getProjects() {
        return projects;
    }

    public void setProjects(List<Long> projects) {
        this.projects = projects;
    }
}
