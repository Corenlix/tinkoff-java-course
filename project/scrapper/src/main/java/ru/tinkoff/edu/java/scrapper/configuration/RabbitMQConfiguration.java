package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    private final static String DLQ = ".dlq";

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public DirectExchange exchange(ApplicationConfig config) {
        return new DirectExchange(config.rabbitExchangeName(), true, false);
    }

    @Bean
    public Queue queue(ApplicationConfig config) {
        return QueueBuilder
                .durable(config.rabbitQueueName())
                .withArgument("x-dead-letter-exchange", config.rabbitExchangeName() + DLQ)
                .withArgument("x-dead-letter-routing-key", config.rabbitRoutingKey() + DLQ)
                .build();
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange, ApplicationConfig config) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(config.rabbitRoutingKey());
    }
}
