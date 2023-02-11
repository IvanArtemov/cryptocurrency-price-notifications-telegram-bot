package com.vanart.okxnotificationbot.sm.executor;

import com.vanart.okxnotificationbot.OkxNotificationsBot;
import com.vanart.okxnotificationbot.dto.BotUpdate;
import com.vanart.okxnotificationbot.sm.SMEvents;
import com.vanart.okxnotificationbot.sm.SMStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ClearStateMachineActionExecutor extends SMActionExecutor {
    private static final Logger LOGGER = Logger.getLogger(ClearStateMachineActionExecutor.class.getName());
    private final OkxNotificationsBot bot;

    @Autowired
    public ClearStateMachineActionExecutor(OkxNotificationsBot bot) {
        this.bot = bot;
    }

    @Override
    public void executeAction(BotUpdate event, StateContext<SMStates, SMEvents> context) {

    }
}
