package by.grsu.iot.api.service.producer;


import by.grsu.iot.api.model.dto.email.EmailCode;

public interface EmailCodeProducer {
    void produce(EmailCode task);
}
