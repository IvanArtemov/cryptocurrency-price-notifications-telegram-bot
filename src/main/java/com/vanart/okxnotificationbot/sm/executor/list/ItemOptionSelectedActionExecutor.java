package com.vanart.okxnotificationbot.sm.executor.list;

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
public class ItemOptionSelectedActionExecutor extends SMActionExecutor {
    private static final Logger LOGGER = Logger.getLogger(ItemOptionSelectedActionExecutor.class.getName());

    @Autowired
    public ItemOptionSelectedActionExecutor() {
    }

    @Override
    public void executeAction(BotUpdate update, StateContext<SMStates, SMEvents> context) {
        if (Constants.REMOVE.equals(update.data())) {
            SMUtils.sendEvent(SMEvents.REMOVE_BUTTON_CLICKED, update, context);
        } else if (Constants.BACK.equals(update.data())) {
            SMUtils.sendEvent(SMEvents.BACK_BUTTON_CLICKED, update, context);
        }
    }
}
