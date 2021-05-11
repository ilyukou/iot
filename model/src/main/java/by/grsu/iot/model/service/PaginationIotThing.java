package by.grsu.iot.model.service;

import by.grsu.iot.model.dto.thing.ThingEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ilyukou Ilya
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginationIotThing {

    private Long id;
    private ThingEnum type;
}
