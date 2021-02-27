package by.grsu.iot.service.validation.factory;

import org.springframework.validation.DataBinder;

public interface DataBinderFactory {

    <T> DataBinder createDataBinder(T t);
}
