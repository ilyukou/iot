package by.grsu.iot.model.sql;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "email")
public class Email extends BaseEntity {

    private String address;

    private String code;

    @OneToOne(mappedBy = "email")
    private User user;

    public Email(Long id, Date created, Date updated, Status status, String address, String code, User user) {
        super(id, created, updated, status);
        this.address = address;
        this.code = code;
        this.user = user;
    }

    public Email(Email email) {
        this(email.getId(), email.getCreated(), email.getUpdated(), email.getStatus(),
                email.getAddress(),
                email.getCode(),
                email.getUser());
    }

    public Email(BaseEntity baseEntity) {
        super(baseEntity);
    }

    public Email() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email)) return false;
        if (!super.equals(o)) return false;
        Email email = (Email) o;
        return Objects.equals(address, email.address) && Objects.equals(code, email.code)
//                && Objects.equals(user, email.user)
                ;
    }
}
