package by.grsu.iot.producer.interf;

import by.grsu.iot.model.async.EmailCode;

public interface EmailCodeProducer {
    void produce(EmailCode task);
}
