package com.vanart.okxnotificationbot.sm.executor;

import com.vanart.okxnotificationbot.dto.BotUpdate;
import com.vanart.okxnotificationbot.repository.StateMachineRepository;
import com.vanart.okxnotificationbot.sm.SMEvents;
import com.vanart.okxnotificationbot.sm.SMStates;
import com.vanart.okxnotificationbot.sm.SMUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public abstract class SMActionExecutor implements Action<SMStates, SMEvents> {

    @Autowired
    private StateMachineRepository stateMachineRepository;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SMActionExecutor.class.getName());
    @Override
    public void execute(StateContext<SMStates, SMEvents> context) {
        BotUpdate event = SMUtils.getBotMessage(context);
        try {
            executeAction(event, context);
        } catch (Exception e) {
            LOGGER.error("SMAction execute error", e);
            stateMachineRepository.remove(event.chatId());
        }
    }

    public abstract void executeAction(BotUpdate event, StateContext<SMStates, SMEvents> context);
}
