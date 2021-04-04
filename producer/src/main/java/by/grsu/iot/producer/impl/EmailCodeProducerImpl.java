package by.grsu.iot.producer.impl;

import by.grsu.iot.model.async.EmailCode;
import by.grsu.iot.producer.interf.EmailCodeProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@PropertySource("classpath:application-producer.properties")
@Service
public class EmailCodeProducerImpl implements EmailCodeProducer {

    @Value("${active-mq.topic.emailVerificationCode}")
    private String TOPIC_NAME;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailCodeProducerImpl.class);

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public EmailCodeProducerImpl(JmsTemplate jmsTemplate, ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void produce(EmailCode task) {
        try {
            LOGGER.info("Attempting Send message to Topic: " + TOPIC_NAME + " | " + task);
            jmsTemplate.convertAndSend(TOPIC_NAME, objectMapper.writeValueAsString(task));
        } catch (Exception e) {
            LOGGER.error("Received Exception during send Message: ", e);
        }
    }
}
