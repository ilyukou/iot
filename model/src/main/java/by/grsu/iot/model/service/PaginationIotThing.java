package by.grsu.iot.model.service;

import by.grsu.iot.model.dto.thing.ThingEnum;

/**
 * @author Ilyukou Ilya
 */
public class PaginationIotThing {

    private Long id;
    private ThingEnum type;

    public PaginationIotThing(Long id, ThingEnum type) {
        this.id = id;
        this.type = type;
    }

    public PaginationIotThing() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ThingEnum getType() {
        return type;
    }

    public void setType(ThingEnum type) {
        this.type = type;
    }
}
