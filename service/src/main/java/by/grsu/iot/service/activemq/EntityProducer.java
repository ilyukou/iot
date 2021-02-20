package by.grsu.iot.service.activemq;

import by.grsu.iot.model.activemq.ActActiveMQ;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class EntityProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityProducer.class);

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Value("${active-mq.topic.project}")
    private String projectTopicName;

    @Value("${active-mq.topic.user}")
    private String userTopicName;

    public EntityProducer(JmsTemplate jmsTemplate, ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(Project project, ActActiveMQ actActiveMQ) {

//        EntityActiveMQ entityActiveMQ = new EntityActiveMQ(actActiveMQ, project.getId(),
//                EntityTypeActiveMQ.PROJECT);
//
//        try {
//            LOGGER.info("Attempting Send message to Topic: " + userTopicName + " | " + entityActiveMQ);
//            jmsTemplate.convertAndSend(projectTopicName, objectMapper.writeValueAsString(entityActiveMQ));
//        } catch (Exception e) {
//            LOGGER.error("Received Exception during send Message: ", e);
//        }
    }

    public void sendMessage(User user, ActActiveMQ actActiveMQ) {

//        EntityActiveMQ entityActiveMQ = new EntityActiveMQ(actActiveMQ, user.getId(),
//                EntityTypeActiveMQ.USER);
//
//        try {
//            LOGGER.info("Attempting Send message to Topic: " + userTopicName + " | " + entityActiveMQ);
//            jmsTemplate.convertAndSend(userTopicName, objectMapper.writeValueAsString(entityActiveMQ));
//        } catch (Exception e) {
//            LOGGER.error("Received Exception during send Message: ", e);
//        }
    }
}
