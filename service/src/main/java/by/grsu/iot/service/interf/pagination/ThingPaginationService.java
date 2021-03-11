package by.grsu.iot.service.interf.pagination;

import by.grsu.iot.model.sql.IotThing;
import by.grsu.iot.service.domain.PaginationInfo;

import java.util.List;

public interface ThingPaginationService {
    PaginationInfo getPaginationInfoA(Long project, String username);

    List<? extends IotThing> getThingsFromProjectPage(Long project, Integer count, String username);
}
