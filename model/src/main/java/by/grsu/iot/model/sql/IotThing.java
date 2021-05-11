package by.grsu.iot.model.sql;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.util.Date;
import java.util.Objects;

/**
 * Superclass for iot thing.
 * In currently version is a only {@link Device}
 *
 * @author Ilyukou Ilya
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IotThing extends BaseEntity {

    private String name;

    private String token;

    private Date active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectId")
    private Project project;

    public IotThing(Long id, Date created, Date updated, Status status, String name, String token, Date active) {
        super(id, created, updated, status);
        this.name = name;
        this.token = token;
    }

    public IotThing(IotThing iotThing) {
        this(iotThing.getId(), iotThing.getCreated(), iotThing.getUpdated(), iotThing.getStatus(),
                iotThing.getName(), iotThing.getToken(), iotThing.getActive());
    }


    public IotThing(Project project, String name, String token) {
        this.name = name;
        this.token = token;
    }

    public IotThing(BaseEntity baseEntity, String name, String token) {
        super(baseEntity);
        this.name = name;
        this.token = token;
    }

    public IotThing(BaseEntity baseEntity) {
        super(baseEntity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IotThing)) return false;
        if (!super.equals(o)) return false;
        IotThing iotThing = (IotThing) o;
        return Objects.equals(name, iotThing.name)
                && Objects.equals(token, iotThing.token)
                && Objects.equals(active, iotThing.active);
    }
}
