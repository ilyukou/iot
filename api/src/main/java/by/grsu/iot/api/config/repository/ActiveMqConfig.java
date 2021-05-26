package by.grsu.iot.api.config.repository;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

//@PropertySource("classpath:application-repository.properties")
@Configuration
public class ActiveMqConfig {

    private final static String MODULE = "by.grsu.iot.repository.";

    private final static String BROKER_URL_PROPERTY = MODULE + "active-mq.broker-url";
    private final static String PASSWORD_PROPERTY = MODULE + "active-mq.password";
    private final static String USER_PROPERTY = MODULE + "active-mq.user";

    private final Environment environment;

    public ActiveMqConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(environment.getProperty(BROKER_URL_PROPERTY));
        activeMQConnectionFactory.setUserName(environment.getProperty(USER_PROPERTY));
        activeMQConnectionFactory.setPassword(environment.getProperty(PASSWORD_PROPERTY));
        return activeMQConnectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory());
        jmsTemplate.setPubSubDomain(true);  // enable for Pub Sub to topic. Not Required for Queue.
        return jmsTemplate;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setPubSubDomain(true);
        return factory;
    }
}
