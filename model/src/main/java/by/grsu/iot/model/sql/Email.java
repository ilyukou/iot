package by.grsu.iot.model.sql;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "email")
public class Email extends BaseEntity {

    private String address;

    private String code;

    @OneToOne(mappedBy = "email")
    private User user;

    public Email(Long id, String address, String code, User user) {
        super(id);
        this.address = address;
        this.code = code;
        this.user = user;
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
}
