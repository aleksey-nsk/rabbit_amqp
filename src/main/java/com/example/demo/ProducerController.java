package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping("/emit/error")
    @ResponseBody
    String emitError() {
        final String message = "error message";
        logger.info("Отправляем сообщение '" + message + "' в обменник");

        // При отправке сообщения в обменник указываем routing key
        template.convertAndSend(Utils.keyError, message);

        return "Сообщение '" + message + "' было отправлено в обменник";
    }

    @RequestMapping("/emit/info")
    @ResponseBody
    String emitInfo() {
        final String message = "info message";
        logger.info("Отправляем сообщение '" + message + "' в обменник");

        // При отправке сообщения в обменник указываем routing key
        template.convertAndSend(Utils.keyInfo, message);

        return "Сообщение '" + message + "' было отправлено в обменник";
    }

    @RequestMapping("/emit/warning")
    @ResponseBody
    String emitWarning() {
        final String message = "warning message";
        logger.info("Отправляем сообщение '" + message + "' в обменник");

        // При отправке сообщения в обменник указываем routing key
        template.convertAndSend(Utils.keyWarning, message);

        return "Сообщение '" + message + "' было отправлено в обменник";
    }
}
