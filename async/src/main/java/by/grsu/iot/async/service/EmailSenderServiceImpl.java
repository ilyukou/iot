package by.grsu.iot.async.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSenderServiceImpl.class);

    private static final String MODULE = "by.grsu.iot.email.";
    private static final String SMTP_EMAIL_PROPERTY = MODULE + "smtp.email";

    private static String from;

    private final JavaMailSender emailSender;

    public EmailSenderServiceImpl(Environment environment, JavaMailSender emailSender) {
        this.emailSender = emailSender;
        from = environment.getProperty(SMTP_EMAIL_PROPERTY);
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
