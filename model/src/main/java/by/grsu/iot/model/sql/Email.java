package by.grsu.iot.model.sql;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

/**
 * @author Ilyukou Ilya
 */
@Entity
@Table(name = "email")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Email extends BaseEntity {

    private String address;

    private Integer code;

    @OneToOne(mappedBy = "email")
    private User user;

    public Email(Long id, Date created, Date updated, Status status, String address, Integer code, User user) {
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
