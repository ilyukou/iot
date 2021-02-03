package by.grsu.iot.model.sql;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "project")
public class Project extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "project")
    private Set<Device> devices = new HashSet<>();

    private String name;

    private AccessType accessType;

    private String title;

    public Project(Long id, Set<Device> devices, String name, AccessType accessType, String title) {
        super(id);
        this.devices = devices;
        this.name = name;
        this.accessType = accessType;
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

    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
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
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        return super.getId().equals(project.getId());
    }

    @Override
    public int hashCode() {
        return super.getId() != null ? super.getId().hashCode() : 0;
    }
}
