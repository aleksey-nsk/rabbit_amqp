package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    RabbitTemplate template;

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "This is home page";
    }

    @RequestMapping("/emit")
    @ResponseBody
    String emit() {
        final String currentTime = (new Date()).toString();
        final String message = "test message " + currentTime;
        logger.info("Отправляем сообщение '" + message + "' в обменник '" + Utils.fanoutExchange + "'");

        template.setExchange(Utils.fanoutExchange); // указываем обменник
        template.convertAndSend(message); // отправляем сообщение в обменник

        return "Сообщение '" + message + "' было отправлено";
    }
}
