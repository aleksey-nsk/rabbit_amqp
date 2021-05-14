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

import static com.example.demo.RabbitParams.*;

@EnableRabbit
@Configuration
public class RabbitConfiguration {

    public static final Logger logger = LogManager.getLogger(RabbitConfiguration.class);

    private ConnectionFactory connectionFactory() {
        logger.info("Настраиваем соединение с RabbitMQ");
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());

        logger.info("Указываем обменник: '" + directExchange + "'");
        rabbitTemplate.setExchange(directExchange);

        return rabbitTemplate;
    }

    @Bean
    public Queue myQueue1() {
        logger.info("Объявляем очередь: '" + queue1 + "'");
        return new Queue(queue1);
    }

    @Bean
    public Queue myQueue2() {
        logger.info("Объявляем очередь: '" + queue2 + "'");
        return new Queue(queue2);
    }

    // Используем routing key, в зависимости от которого сообщение может попасть
    // в одну из очередей или сразу в обе.
    // Для этого нужен DirectExchange
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(directExchange);
    }

    @Bean
    public Binding errorBinding1() {
        return BindingBuilder.bind(myQueue1()).to(directExchange()).with(keyError);
    }

    @Bean
    public Binding errorBinding2() {
        return BindingBuilder.bind(myQueue2()).to(directExchange()).with(keyError);
    }

    @Bean
    public Binding infoBinding() {
        return BindingBuilder.bind(myQueue2()).to(directExchange()).with(keyInfo);
    }

    @Bean
    public Binding warningBinding() {
        return BindingBuilder.bind(myQueue2()).to(directExchange()).with(keyWarning);
    }
}
