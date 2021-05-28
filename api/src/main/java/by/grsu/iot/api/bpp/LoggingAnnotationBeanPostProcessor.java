package by.grsu.iot.api.bpp;

import by.grsu.iot.api.model.annotation.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoggingAnnotationBeanPostProcessor implements BeanPostProcessor {

    private final Map<String, Class> map = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();

        if (beanClass.isAnnotationPresent(Logging.class)) {
            map.put(beanName, beanClass);
            return bean;
        }


        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = map.get(beanName);

        if (beanClass == null) {
            return bean;
        }

        Logger LOGGER = LoggerFactory.getLogger(beanClass);

        return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                LOGGER.trace("Call method {} with args {}", method.getName(), Arrays.toString(args));

                Object returnValue = method.invoke(bean, args);

                LOGGER.trace(method.getName() + " return " + returnValue.toString());

                return returnValue;
            }
        });
    }
}
