package by.grsu.iot.repository.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@PropertySource("classpath:application.properties")
@Configuration
public class RepositoryActiveMQConfig {

    private final Environment env;

    public RepositoryActiveMQConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(env.getProperty("active-mq.broker-url"));
        activeMQConnectionFactory.setUserName(env.getProperty("active-mq.user"));
        activeMQConnectionFactory.setPassword(env.getProperty("active-mq.password"));
        return activeMQConnectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory());
        jmsTemplate.setPubSubDomain(true);  // enable for Pub Sub to topic. Not Required for Queue.
        return jmsTemplate;
    }
}
