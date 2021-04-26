package by.grsu.iot.email.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Objects;
import java.util.Properties;

@PropertySource("classpath:application-email.properties")
@Configuration
public class SmtpConfig {

    private final String module = "by.grsu.iot.email.";

    private final String host = module + "smtp.host";
    private final String port = module + "smtp.port";
    private final String username = module + "smtp.email";
    private final String password = module + "smtp.password";

    private final String mailTransportProtocol = "mail.transport.protocol";
    private final String mailSmtpAuth = "mail.smtp.auth";
    private final String mailSmtpStarttlsEnable = "mail.smtp.starttls.enable";
    private final String maildebug = "mail.debug";

    private final Environment environment;

    public SmtpConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(environment.getProperty(host));
        mailSender.setPort(Integer.parseInt(Objects.requireNonNull(environment.getProperty(port))));

        mailSender.setUsername(environment.getProperty(username));
        mailSender.setPassword(environment.getProperty(password));

        Properties props = mailSender.getJavaMailProperties();
        props.put(mailTransportProtocol, environment.getProperty(module + mailTransportProtocol));
        props.put(mailSmtpAuth, environment.getProperty(module + mailSmtpAuth));
        props.put(mailSmtpStarttlsEnable, environment.getProperty(module + mailSmtpStarttlsEnable));
        props.put(maildebug, environment.getProperty(module + maildebug));

        return mailSender;
    }
}
