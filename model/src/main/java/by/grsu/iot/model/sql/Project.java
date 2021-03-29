package by.grsu.iot.model.sql;

import javax.persistence.*;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private Set<Sensor> sensors = new HashSet<>();

    private String name;

    private String title;

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

    public Set<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(Set<Sensor> sensors) {
        this.sensors = sensors;
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
