package by.grsu.iot.model.sql;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class IotThing extends BaseEntity {

    private String name;

    private String token;

    public IotThing(Long id, String name, String token) {
        super(id);
        this.name = name;
        this.token = token;
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

    public IotThing() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
