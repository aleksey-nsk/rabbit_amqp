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

    // Первый листенер
    @RabbitListener(queues = Utils.queue1)
    public void listener1(String message) throws InterruptedException {
        Thread.sleep(2_000L); // эмуляция полезной работы
        logger.info("[listener 1] Из очереди '" + Utils.queue1 + "' получено сообщение '" + message + "'");
    }

    // Второй листенер
    @RabbitListener(queues = Utils.queue2)
    public void listener2(String message) throws InterruptedException {
        Thread.sleep(10_000L);
        logger.info("[listener 2] Из очереди '" + Utils.queue2 + "' получено сообщение '" + message + "'");
    }
}
