package com.vanart.okxnotificationbot;

import com.vanart.okxnotificationbot.dto.BotUpdate;
import com.vanart.okxnotificationbot.repository.StateMachineRepository;
import com.vanart.okxnotificationbot.sm.SMEvents;
import com.vanart.okxnotificationbot.sm.SMStates;
import com.vanart.okxnotificationbot.sm.SMUtils;
import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

@Service
public class BotEventProcessorImpl implements BotEventProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(BotEventProcessorImpl.class.getName());
    private final StateMachineRepository stateMachineRepository;
    private final StateMachineFactory<SMStates, SMEvents> stateMachineFactory;
    private final OkxNotificationsBot bot;

    @Autowired
    public BotEventProcessorImpl(StateMachineRepository stateMachineRepository, StateMachineFactory<SMStates, SMEvents> stateMachineFactory, OkxNotificationsBot bot) {
        this.stateMachineRepository = stateMachineRepository;
        this.stateMachineFactory = stateMachineFactory;
        this.bot = bot;
    }

    @PostConstruct
    private void init() {
        bot.setEventProcessor(this);
    }

    @Override
    public void processMessage(@NotNull BotUpdate update) {
        var text = update.data();
        var chatId = update.chatId();
        StateMachine<SMStates, SMEvents> stateMachine;
        if (text.startsWith("/")) {
            stateMachine = stateMachineFactory.getStateMachine();
            stateMachine.startReactively().subscribe();
            stateMachineRepository.save(chatId, stateMachine);
            var event = SMEvents.getByCommand(text);
            if (event.isPresent()) {
                stateMachine
                        .sendEvent(SMUtils.buildMessageEvent(event.get(), update))
                        .subscribe();
            } else {
                LOGGER.error("Unexpected command");
            }
        } else {
            var stateMachineOptional = stateMachineRepository.find(chatId);
            stateMachineOptional.ifPresent(statesEventsStateMachine -> statesEventsStateMachine
                    .sendEvent(SMUtils.buildMessageEvent(SMEvents.MESSAGE_RECEIVED, update))
                    .subscribe());
        }
    }

    @Override
    public void buttonClick(BotUpdate update) {
        var stateMachineOptional = stateMachineRepository.find(update.chatId());
        stateMachineOptional.ifPresent(statesEventsStateMachine -> statesEventsStateMachine
                .sendEvent(SMUtils.buildButtonClickedEvent(SMEvents.BUTTON_CLICKED, update))
                .subscribe());
    }
}
