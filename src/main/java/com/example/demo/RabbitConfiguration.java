package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitConfiguration {

    public static final Logger logger = LogManager.getLogger(RabbitConfiguration.class);

    // Настраиваем соединение с RabbitMQ
    private ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());

        logger.info("Указываем обменник: '" + Utils.directExchange + "'");
        rabbitTemplate.setExchange(Utils.directExchange);

        return rabbitTemplate;
    }

    // Объявляем очередь
    @Bean
    public Queue myQueue1() {
        logger.info("Объявляем очередь: '" + Utils.queue1 + "'");
        return new Queue(Utils.queue1);
    }

    @Bean
    public Queue myQueue2() {
        logger.info("Объявляем очередь: '" + Utils.queue2 + "'");
        return new Queue(Utils.queue2);
    }

    // Используем routing key, в зависимости от которого сообщение может попасть
    // в одну из очередей или сразу в обе.
    // Для этого используем DirectExchange
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(Utils.directExchange);
    }

    @Bean
    public Binding errorBinding1() {
        return BindingBuilder.bind(myQueue1()).to(directExchange()).with(Utils.keyError);
    }

    @Bean
    public Binding errorBinding2() {
        return BindingBuilder.bind(myQueue2()).to(directExchange()).with(Utils.keyError);
    }

    @Bean
    public Binding infoBinding() {
        return BindingBuilder.bind(myQueue2()).to(directExchange()).with(Utils.keyInfo);
    }

    @Bean
    public Binding warningBinding() {
        return BindingBuilder.bind(myQueue2()).to(directExchange()).with(Utils.keyWarning);
    }
}
