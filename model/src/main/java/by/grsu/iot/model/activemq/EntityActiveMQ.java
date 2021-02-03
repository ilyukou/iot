package by.grsu.iot.model.activemq;

import java.io.Serializable;

/**
 * The change of the object is transmitted by the producer to the consumer,
 * which must be repeated in the search database
 */
public class EntityActiveMQ implements Serializable {

    private static final long serialVersionUID = -295422703255886286L;

    // What need to do
    private ActActiveMQ actActiveMQ;

    // Entity id
    private Long entityId;

    // Type of Entity
    private EntityTypeActiveMQ entityTypeActiveMQ;

    public EntityActiveMQ(ActActiveMQ actActiveMQ, Long entityId, EntityTypeActiveMQ entityTypeActiveMQ) {
        this.actActiveMQ = actActiveMQ;
        this.entityId = entityId;
        this.entityTypeActiveMQ = entityTypeActiveMQ;
    }

    public EntityActiveMQ() {
    }

    public ActActiveMQ getActActiveMQ() {
        return actActiveMQ;
    }

    public void setActActiveMQ(ActActiveMQ actActiveMQ) {
        this.actActiveMQ = actActiveMQ;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public EntityTypeActiveMQ getEntityTypeActiveMQ() {
        return entityTypeActiveMQ;
    }

    public void setEntityTypeActiveMQ(EntityTypeActiveMQ entityTypeActiveMQ) {
        this.entityTypeActiveMQ = entityTypeActiveMQ;
    }

    @Override
    public String toString() {
        return "EntityActiveMQ{" +
                "actActiveMQ=" + actActiveMQ +
                ", entityId=" + entityId +
                ", entityTypeActiveMQ=" + entityTypeActiveMQ +
                '}';
    }
}
