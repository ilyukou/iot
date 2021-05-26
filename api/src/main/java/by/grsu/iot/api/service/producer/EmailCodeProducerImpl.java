package by.grsu.iot.api.service.producer;

import by.grsu.iot.api.model.dto.email.EmailCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailCodeProducerImpl implements EmailCodeProducer {

    @Value("${by.grsu.iot.api.service.producer.emailVerificationCode}")
    private String TOPIC_NAME_PROPERTY;

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
            LOGGER.info("Attempting Send message to Topic: " + TOPIC_NAME_PROPERTY + " | " + task);
            jmsTemplate.convertAndSend(TOPIC_NAME_PROPERTY, objectMapper.writeValueAsString(task));
        } catch (Exception e) {
            LOGGER.error("Received Exception during send Message: ", e);
        }
    }
}
