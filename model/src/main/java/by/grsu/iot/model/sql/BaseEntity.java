package by.grsu.iot.model.sql;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Superclass which contains base filed (id, created time, updated time and status) for any object.
 *
 * @author Ilyukou Ilya
 */
@MappedSuperclass
public class BaseEntity implements Serializable, Comparable<BaseEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created")
    private Date created;

    @LastModifiedDate
    @Column(name = "updated")
    private Date updated;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public BaseEntity(Long id, Date created, Date updated, Status status) {
        this.id = id;
        this.created = created;
        this.updated = updated;
        this.status = status;
    }

    public BaseEntity() {
    }

    public BaseEntity(BaseEntity baseEntity) {
        this(baseEntity.getId(), baseEntity.getCreated(), baseEntity.getUpdated(), baseEntity.getStatus());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isActive() {
        return getStatus() == Status.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity)) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(id, that.id)
                && Objects.equals(created, that.created)
                && Objects.equals(updated, that.updated)
                && Objects.equals(status, that.status);
    }

    @Override
    public int compareTo(BaseEntity o) {
        if (this.id.equals(o.id)) {
            return 0;
        } else if (this.id < o.id) {
            return -1;
        } else {
            return 1;
        }
    }
}