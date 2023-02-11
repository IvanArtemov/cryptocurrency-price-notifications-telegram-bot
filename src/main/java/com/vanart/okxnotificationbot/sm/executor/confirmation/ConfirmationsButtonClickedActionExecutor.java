package com.vanart.okxnotificationbot.sm.executor.confirmation;

import com.vanart.okxnotificationbot.Constants;
import com.vanart.okxnotificationbot.dto.BotUpdate;
import com.vanart.okxnotificationbot.sm.SMEvents;
import com.vanart.okxnotificationbot.sm.SMStates;
import com.vanart.okxnotificationbot.sm.SMUtils;
import com.vanart.okxnotificationbot.sm.executor.SMActionExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ConfirmationsButtonClickedActionExecutor extends SMActionExecutor {
    private static final Logger LOGGER = Logger.getLogger(ConfirmationsButtonClickedActionExecutor.class.getName());

    @Autowired
    public ConfirmationsButtonClickedActionExecutor() {
    }

    @Override
    public void executeAction(BotUpdate event, StateContext<SMStates, SMEvents> context) {
        if (Constants.YES.equals(event.data())) {
            SMUtils.sendEvent(SMEvents.CONFIRMATION_YES_CLICKED, event, context);
        } else {
            SMUtils.sendEvent(SMEvents.CONFIRMATION_NO_CLICKED, event, context);
        }
    }
}
