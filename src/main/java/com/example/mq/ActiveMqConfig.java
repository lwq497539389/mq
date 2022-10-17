package com.example.mq;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class ActiveMqConfig {
    /**
     * one mq 地址 账户密码注入
     *
     * @param brokerUrl
     * @param username
     * @param password
     * @return
     */
    @Bean(name = "oneConnectionFactory")
    @Primary
    public ActiveMQConnectionFactory oneConnectionFactory(
            @Value("${activemq.one.brokerUrl}") String brokerUrl,
            @Value("${activemq.one.user}") String username,
            @Value("${activemq.one.password}") String password) {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL(brokerUrl);
        factory.setUserName(username);
        factory.setPassword(password);
        return factory;
    }

    /**
     * one JmsTemplate生成
     *
     * @param connectionFactory
     * @param pubSubDmain
     * @return
     */
    @Bean(name = "oneJmsTemplate")
    @Primary
    public JmsTemplate oneJmsTemplate(
            @Qualifier("oneConnectionFactory") ActiveMQConnectionFactory connectionFactory,
            @Value("${activemq.one.pub-sub-domain}") boolean pubSubDmain) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setPubSubDomain(pubSubDmain);
        return jmsTemplate;
    }

    /**
     * one JmsListener工厂生成
     *
     * @param connectionFactory
     * @param pubSubDmain
     * @return
     */
    @Bean(name = "oneJmsListenerContainerFactory")
    @Primary
    public JmsListenerContainerFactory oneJmsListenerContainerFactory(
            @Qualifier("oneConnectionFactory") ActiveMQConnectionFactory connectionFactory,
            @Value("${activemq.one.pub-sub-domain}") boolean pubSubDmain) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(pubSubDmain);
        return factory;
    }


    /**
     * two mq 地址 账号密码注入
     *
     * @param brokerUrl
     * @param username
     * @param password
     * @return
     */
    @Bean(name = "twoConnectionFactory")
    public ActiveMQConnectionFactory twoConnectionFactory(
            @Value("${activemq.two.brokerUrl}") String brokerUrl,
            @Value("${activemq.two.user}") String username,
            @Value("${activemq.two.password}") String password) {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL(brokerUrl);
        factory.setUserName(username);
        factory.setPassword(password);
        return factory;
    }

    /**
     * two JmsTemplate生成
     *
     * @param connectionFactory
     * @param pubSubDmain
     * @return
     */
    @Bean(name = "twoJmsTemplate")
    public JmsTemplate twoJmsTemplate(
            @Qualifier("twoConnectionFactory") ActiveMQConnectionFactory connectionFactory,
            @Value("${activemq.two.pub-sub-domain}") boolean pubSubDmain) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setPubSubDomain(pubSubDmain);
        return jmsTemplate;
    }

    /**
     * two JmsListener工厂生成
     *
     * @param connectionFactory
     * @param pubSubDmain
     * @return
     */
    @Bean(name = "twoJmsListenerContainerFactory")
    public JmsListenerContainerFactory twoJmsListenerContainerFactory(
            @Qualifier("twoConnectionFactory") ActiveMQConnectionFactory connectionFactory,
            @Value("${activemq.two.pub-sub-domain}") boolean pubSubDmain) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        //单条信息确认
        factory.setSessionAcknowledgeMode(ActiveMQSession.INDIVIDUAL_ACKNOWLEDGE);
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(pubSubDmain);
        return factory;
    }
}


