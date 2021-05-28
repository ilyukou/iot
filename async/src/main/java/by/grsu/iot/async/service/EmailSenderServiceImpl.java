package by.grsu.iot.async.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSenderServiceImpl.class);

    @Value("${by.grsu.iot.email.smtp.email}")
    private String from;

    private final JavaMailSender emailSender;

    public EmailSenderServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendMessage(String to, String subject, String text) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            helper.setTo(to);
            helper.setText(text, true);
            helper.setSubject(subject);
            helper.setFrom(from);

            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.warn("MessagingException", e);
        }

    }
}
