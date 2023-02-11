package com.vanart.okxnotificationbot.sm.executor;

import com.vanart.okxnotificationbot.dto.BotUpdate;
import com.vanart.okxnotificationbot.sm.SMEvents;
import com.vanart.okxnotificationbot.sm.SMStates;
import com.vanart.okxnotificationbot.sm.SMUtils;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public abstract class SMActionExecutor implements Action<SMStates, SMEvents> {
    @Override
    public void execute(StateContext<SMStates, SMEvents> context) {
        BotUpdate event = SMUtils.getBotMessage(context);
        executeAction(event, context);
    }

    public abstract void executeAction(BotUpdate event, StateContext<SMStates, SMEvents> context);
}
