package by.grsu.iot.model.sql;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author Ilyukou Ilya
 */
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    @Column(name = "role")
    private RoleType role;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private List<User> users;

    public Role(RoleType role) {
        super();
        this.role = role;
    }

    public Role(Long id, Date created, Date updated, Status status, RoleType role, List<User> users) {
        super(id, created, updated, status);
        this.role = role;
        this.users = users;
    }

    public Role(BaseEntity baseEntity) {
        super(baseEntity);
    }

    public Role() {
        super();
    }

    public Role(Role role) {
        this(role.getId(), role.getCreated(), role.getUpdated(), role.getStatus(),
                role.getRoleType(), role.getUsers());
    }

    public RoleType getRoleType() {
        return role;
    }

    public void setRoleType(RoleType role) {
        this.role = role;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id: " + super.getId() + ", " +
                "name: " + role + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        if (!super.equals(o)) return false;
        Role role1 = (Role) o;
        return role == role1.role
//                && Objects.equals(users, role1.users)
                ;
    }
}
