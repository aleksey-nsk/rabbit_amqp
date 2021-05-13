package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// В качестве продюссера будет контроллер, который будет посылать сообщения в RabbitMQ
@Controller
public class ProducerController {

    public static final Logger logger = LogManager.getLogger(ProducerController.class);

    @Autowired
    AmqpTemplate template;

    @RequestMapping("/emit")
    @ResponseBody
    String sendMessage() {
        String message;

        for (int i = 1; i <= 10; i++) {
            message = "test message " + i;
            logger.info("Отправляем сообщение '" + message + "' в очередь '" + Utils.queueName + "'");
            template.convertAndSend(Utils.queueName, message); // отправляем сообщение
        }

        return "Все сообщения были отправлены";
    }
}
