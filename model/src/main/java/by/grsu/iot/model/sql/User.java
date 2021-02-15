package by.grsu.iot.model.sql;

import org.hibernate.proxy.HibernateProxyHelper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "user")
public class User extends BaseEntity implements UserDetails {

    private String username;

    private String password;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "email_id")
    private Email email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Project> projects = new HashSet<>();

    // FIXME - LAZY INTITIAL
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles = new ArrayList<>();

    public User() {
    }

    public User(Long id, Date created, Date updated, Status status,
                String username, String password, Email email,
                Set<Project> projects, List<Role> roles) {
        super(id, created, updated, status);
        this.username = username;
        this.password = password;
        this.email = email;
        this.projects = projects;
        this.roles = roles;
    }

    public User(User user) {
        super(user.getId(), user.getCreated(), user.getUpdated(), user.getStatus());
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.projects = user.getProjects();
        this.roles = user.getRoles();
    }

    public User(BaseEntity baseEntity) {
        super(baseEntity);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // FIXME - add implementation
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public void addProject(Project project){
        this.projects.add(project);
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(username, user.username)
                && Objects.equals(password, user.password)
//                && Objects.equals(email, user.email)
//                && Objects.equals(projects, user.projects)
//                && Objects.equals(roles, user.roles)
                ;
    }
}
