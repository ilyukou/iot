package by.grsu.iot.model.sql;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Ilyukou Ilya
 */
@Entity
@Table(name = "project")
public class Project extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private Set<Device> devices = new HashSet<>();

    private String name;

    private String title;

    public Project(Long id, Date created, Date updated, Status status,
                   User user, Set<Device> devices, String name, String title) {
        super(id, created, updated, status);
        this.user = user;
        this.devices = devices;
        this.name = name;
        this.title = title;
    }

    public Project(BaseEntity baseEntity) {
        super(baseEntity);
    }

    public Project() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Device> getDevices() {
        return devices;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        if (!super.equals(o)) return false;
        Project project = (Project) o;
        return
//                Objects.equals(user, project.user)
//                && Objects.equals(devices, project.devices) &&
                Objects.equals(name, project.name)
                        && Objects.equals(title, project.title);
    }
}
