package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.example.demo.RabbitParams.*;

// В качестве продюссера будет контроллер,
// который будет посылать сообщения в RabbitMQ
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
        logger.info("Отправляем сообщение '" + errorMessage + "' в обменник. Routing key: '" + keyError + "'");
        template.convertAndSend(keyError, errorMessage);
        return "Сообщение '" + errorMessage + "' было отправлено в обменник";
    }

    @RequestMapping("/emit/info")
    @ResponseBody
    String emitInfo() {
        logger.info("Отправляем сообщение '" + infoMessage + "' в обменник. Routing key: '" + keyInfo + "'");
        template.convertAndSend(keyInfo, infoMessage);
        return "Сообщение '" + infoMessage + "' было отправлено в обменник";
    }

    @RequestMapping("/emit/warning")
    @ResponseBody
    String emitWarning() {
        logger.info("Отправляем сообщение '" + warningMessage + "' в обменник. Routing key: '" + keyWarning + "'");
        template.convertAndSend(keyWarning, warningMessage);
        return "Сообщение '" + warningMessage + "' было отправлено в обменник";
    }
}
