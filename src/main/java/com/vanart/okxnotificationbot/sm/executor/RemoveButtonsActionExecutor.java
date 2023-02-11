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
public class RemoveButtonsActionExecutor extends SMActionExecutor {
    private static final Logger LOGGER = Logger.getLogger(RemoveButtonsActionExecutor.class.getName());
    private final OkxNotificationsBot bot;

    @Autowired
    public RemoveButtonsActionExecutor(OkxNotificationsBot bot) {
        this.bot = bot;
    }

    @Override
    public void executeAction(BotUpdate update, StateContext<SMStates, SMEvents> context) {
        bot.removeButtons(update.chatId(), "1");
    }
}
