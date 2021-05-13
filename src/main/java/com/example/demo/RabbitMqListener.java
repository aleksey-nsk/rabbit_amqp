package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@EnableRabbit // нужна для активации обработки аннотаций @RabbitListener
@Component
public class RabbitMqListener {

    public static final Logger logger = LogManager.getLogger(RabbitMqListener.class);

    // В этот метод будут приходить сообщения
    @RabbitListener(queues = "queue1")
    public void processQueue1(String message) throws InterruptedException {
        Thread.sleep(10_000L); // идёт обработка сообщения
        logger.info("Из очереди 'queue1' получено сообщение: '" + message + "'");
    }
}
