package by.grsu.iot.async.consumer;

import by.grsu.iot.email.interf.EmailSenderService;
import by.grsu.iot.model.async.EmailCode;
import by.grsu.iot.util.service.EmailMessageTemplateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.Message;
import javax.jms.MessageListener;

@PropertySource("classpath:application-async.properties")
@Service
public class EmailCodeConsumer implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailCodeConsumer.class);

    private static final String SUBJECT = "Activation Code";

    private final EmailSenderService emailSenderService;
    private final ObjectMapper objectMapper;

    public EmailCodeConsumer(
            EmailSenderService emailSenderService,
            ObjectMapper objectMapper
    ) {
        this.emailSenderService = emailSenderService;
        this.objectMapper = objectMapper;
    }

    @JmsListener(destination = "${active-mq.topic.emailVerificationCode}")
    @Override
    public void onMessage(Message message) {
        try{

            ActiveMQTextMessage objectMessage = (ActiveMQTextMessage) message;

            EmailCode object = objectMapper.readValue(objectMessage.getText(), EmailCode.class);

            LOGGER.trace("Received Message: "+ object.toString());

            handle(object);

        } catch(Exception e) {
            LOGGER.error("Received Exception : "+ e);
        }
    }

    private void handle(EmailCode emailCode){
        switch (emailCode.getType()){
            case CREATE_ACCOUNT:
                sendCreateAccountLetter(emailCode);
                break;

            case RESTORE_PASSWORD:
                sendRestorePasswordLetter(emailCode);
                break;

            case CHANGE_EMAIL:
                sendChangeEmailLetter(emailCode);
                break;

            default:
                LOGGER.warn("Not found handler for " + emailCode.getType());
        }
    }

    private void sendCreateAccountLetter(EmailCode emailCode) {
        emailSenderService.sendMessage(
                emailCode.getAddress(),
                SUBJECT,
                EmailMessageTemplateUtil.getCreateAccountLetterText(emailCode.getCode()));
    }

    private void sendRestorePasswordLetter(EmailCode emailCode) {
        emailSenderService.sendMessage(
                emailCode.getAddress(),
                SUBJECT,
                EmailMessageTemplateUtil.getRestorePasswordLetterText(emailCode.getCode()));
    }

    private void sendChangeEmailLetter(EmailCode emailCode) {
        emailSenderService.sendMessage(
                emailCode.getAddress(),
                SUBJECT,
                EmailMessageTemplateUtil.getChangeEmailLetterText(emailCode.getCode()));
    }
}
