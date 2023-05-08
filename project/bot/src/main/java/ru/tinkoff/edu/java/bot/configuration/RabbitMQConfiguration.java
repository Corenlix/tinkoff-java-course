package ru.tinkoff.edu.java.bot.configuration;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.dto.LinkUpdate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfiguration {
    private final static String DLQ = ".dlq";

    @Bean
    public ClassMapper classMapper() {
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("ru.tinkoff.edu.java.scrapper.dto.client.tgBot.LinkUpdate", LinkUpdate.class);
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("ru.tinkoff.edu.java.scrapper.dto.tgBot.*");
        classMapper.setIdClassMapping(mappings);
        return classMapper;
    }

    @Bean
    public MessageConverter jsonMessageConverter(ClassMapper classMapper) {
        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        jsonConverter.setClassMapper(classMapper);
        return jsonConverter;
    }

    @Bean
    public DirectExchange deadLetterExchange(ApplicationConfig applicationConfig) {
        return new DirectExchange(applicationConfig.rabbitExchangeName() + DLQ);
    }

    @Bean
    public Queue deadLetterQueue(ApplicationConfig applicationConfig) {
        return QueueBuilder
                .durable(applicationConfig.rabbitQueueName() + DLQ)
                .build();
    }

    @Bean
    public Binding deadLetterbinding(Queue deadLetterQueue, DirectExchange deadLetterExchange, ApplicationConfig applicationConfig) {
        return BindingBuilder
                .bind(deadLetterQueue)
                .to(deadLetterExchange)
                .with(applicationConfig.rabbitRoutingKey() + DLQ);
    }
}
