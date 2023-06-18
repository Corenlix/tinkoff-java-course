package ru.tinkoff.edu.java.bot.bot.updateprocessor;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.List;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.configuration.ApplicationConfig;

@Component
public class UpdateProcessorsChain {
    private final List<UpdateProcessor> updateProcessorList;
    private final TelegramBot telegramBot;
    private final Counter messagesCounter;

    public UpdateProcessorsChain(
            List<UpdateProcessor> updateProcessorList,
            TelegramBot telegramBot,
            MeterRegistry meterRegistry,
            ApplicationConfig applicationConfig) {
        this.updateProcessorList = updateProcessorList;
        this.telegramBot = telegramBot;
        this.messagesCounter = meterRegistry.counter(applicationConfig.micrometerProcessedMessagesCounter());
    }

    public void process(Update update) {
        for (UpdateProcessor updateProcessor : updateProcessorList) {
            var processResponse = updateProcessor.tryProcess(update);
            if (processResponse.isPresent()) {
                messagesCounter.increment();
                telegramBot.execute(processResponse.get());
                return;
            }
        }
    }
}
