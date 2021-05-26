package by.grsu.iot.async.consumer;

import by.grsu.iot.async.model.EmailCode;
import by.grsu.iot.async.model.EmailCodeType;
import by.grsu.iot.async.service.EmailSenderService;
import by.grsu.iot.async.util.EmailMessageTemplateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.Message;
import javax.jms.MessageListener;

import static io.vavr.API.*;

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

    @JmsListener(destination = "${by.grsu.iot.async.active-mq.topic.emailVerificationCode}")
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

    private void handle(EmailCode emailCode) {
        Match(emailCode.getType()).of(
                Case($(t -> t == EmailCodeType.CREATE_ACCOUNT), run(() -> sendCreateAccountLetter(emailCode))),
                Case($(t -> t == EmailCodeType.RESTORE_PASSWORD), run(() -> sendRestorePasswordLetter(emailCode))),
                Case($(t -> t == EmailCodeType.CHANGE_EMAIL), run(() -> sendChangeEmailLetter(emailCode))),
                Case($(), o -> run(() -> LOGGER.warn("Not found handler for " + emailCode.getType()))));
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
