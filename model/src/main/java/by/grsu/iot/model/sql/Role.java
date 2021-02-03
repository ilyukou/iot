package by.grsu.iot.model.sql;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    @Column(name = "role")
    private RoleType role;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;

    public Role(RoleType role) {
        super();
        this.role = role;
    }

    public Role(Long id, RoleType role, List<User> users) {
        super(id);
        this.role = role;
        this.users = users;
    }

    public Role(BaseEntity baseEntity) {
        super(baseEntity);
    }

    public Role() {
        super();
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
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

}
