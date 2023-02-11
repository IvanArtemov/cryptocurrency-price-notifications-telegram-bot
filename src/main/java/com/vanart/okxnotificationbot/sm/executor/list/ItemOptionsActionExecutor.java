package com.vanart.okxnotificationbot.sm.executor.list;

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
public class ItemOptionsActionExecutor extends SMActionExecutor {
    private static final Logger LOGGER = Logger.getLogger(ItemOptionsActionExecutor.class.getName());
    private final OkxNotificationsBot bot;

    @Autowired
    public ItemOptionsActionExecutor(OkxNotificationsBot bot) {
        this.bot = bot;
    }

    @Override
    public void executeAction(BotUpdate event, StateContext<SMStates, SMEvents> context) {
        var buttons = new ArrayList<String>();
        context.getExtendedState().getVariables().put(Constants.VALUE, event.data());
        buttons.add(Constants.REMOVE);
        buttons.add(Constants.BACK);
        bot.showButtons(event.chatId(), "Options: ", buttons);
    }
}
