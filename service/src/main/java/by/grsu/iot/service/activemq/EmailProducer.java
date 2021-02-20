package by.grsu.iot.service.activemq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmailProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailProducer.class);
    private final JmsTemplate jmsTemplate;
    @Value("${active-mq.topic.email}")
    private String emailTopicName;

    public EmailProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(String email) {
//        try {
//            LOGGER.info("Attempting Send message to Topic: " + emailTopicName + " | " + email);
//            jmsTemplate.convertAndSend(emailTopicName, email);
//        } catch (Exception e) {
//            LOGGER.error("Recieved Exception during send Message: ", e);
//        }
    }
}
