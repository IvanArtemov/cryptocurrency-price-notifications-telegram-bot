package com.vanart.okxnotificationbot.sm.executor.confirmation;

import com.vanart.okxnotificationbot.Constants;
import com.vanart.okxnotificationbot.OkxNotificationsBot;
import com.vanart.okxnotificationbot.dto.BotUpdate;
import com.vanart.okxnotificationbot.sm.SMEvents;
import com.vanart.okxnotificationbot.sm.SMStates;
import com.vanart.okxnotificationbot.sm.executor.SMActionExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.logging.Logger;

@Component
public class ConfirmationsActionExecutor extends SMActionExecutor {
    private static final Logger LOGGER = Logger.getLogger(ConfirmationsActionExecutor.class.getName());
    private final OkxNotificationsBot bot;

    @Autowired
    public ConfirmationsActionExecutor(OkxNotificationsBot bot) {
        this.bot = bot;
    }

    @Override
    public void executeAction(BotUpdate event, StateContext<SMStates, SMEvents> context) {
        var buttons = new ArrayList<String>();
        buttons.add(Constants.YES);
        buttons.add(Constants.NO);
        bot.showButtons(event.chatId(), "Are your sure? ", buttons);
    }
}
