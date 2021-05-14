package com.example.demo;

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

    // Настраиваем соединение с RabbitMQ
    private ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    // Объявляем очередь
    @Bean
    public Queue myQueue1() {
        return new Queue(Utils.queue1);
    }

    @Bean
    public Queue myQueue2() {
        return new Queue(Utils.queue2);
    }

    // Теперь одно и то же сообщение должно приходить сразу двум консьюмерам
    // Для этого подключим обе очереди к FanoutExchange
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(Utils.fanoutExchange);
    }

    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(myQueue1()).to(fanoutExchange());
    }

    @Bean
    public Binding binding2() {
        return BindingBuilder.bind(myQueue2()).to(fanoutExchange());
    }
}
