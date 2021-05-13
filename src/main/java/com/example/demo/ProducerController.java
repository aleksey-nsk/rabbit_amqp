package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

// В качестве продюссера будет контроллер, который будет посылать сообщения в RabbitMQ
@Controller
public class ProducerController {

    public static final Logger logger = LogManager.getLogger(ProducerController.class);

    @Autowired
    AmqpTemplate template;

    @RequestMapping("/emit")
    @ResponseBody
    String queue1() {
        final String queueName = "queue1";
        final String currentTime = (new Date()).toString();
        final String message = "test message " + currentTime;

        logger.info("Отправляем сообщение '" + message + "' в очередь '" + queueName + "'");
        template.convertAndSend(queueName, message); // отправляем сообщение

        return "Сообщение '" + message + "' было отправлено";
    }
}
