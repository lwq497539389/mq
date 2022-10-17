package com.example.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {
    @Autowired
    @Qualifier("oneJmsTemplate")
    private JmsTemplate jmsTemplate1;

    @Autowired
    @Qualifier("twoJmsTemplate")
    private JmsTemplate jmsTemplate2;


    @PostMapping("/queue/test")
    public String sendQueue(@RequestBody String str) {
        jmsTemplate1.convertAndSend("one",str);
        return "success";
    }

    @PostMapping("/queue/test2")
    public String sendTopic(@RequestBody String str) {
        jmsTemplate2.convertAndSend("two",str);
        return "success";
    }

}