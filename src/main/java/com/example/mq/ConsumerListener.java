package com.example.mq;

import org.apache.activemq.command.ActiveMQMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.jms.JMSException;

@Component
public class ConsumerListener {

    @Autowired
    @Qualifier("twoJmsTemplate")
    private JmsTemplate jmsTemplate2;


    @JmsListener(destination = "${msgListener.oneMqName}", containerFactory = "oneJmsListenerContainerFactory")
    public void receiveQueueOne(String message) {
        if (StringUtils.isEmpty(message)) {
            return;
        }
        // todo
        System.out.println("接收到mq1:" + message);
        jmsTemplate2.convertAndSend("two1",message);
    }

    /**
     * 接收two mq方法
     *
     */
    @JmsListener(destination = "${msgListener.twoMqName}", containerFactory = "twoJmsListenerContainerFactory")
    public void receiveQueueTwo(ActiveMQMessage message) throws JMSException {
        if (StringUtils.isEmpty(message)) {
            return;
        }
        // todo
        System.out.println("接收到mq2:" + message);
        message.acknowledge();
    }


}