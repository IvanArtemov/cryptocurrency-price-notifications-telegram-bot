package com.vanart.okxnotificationbot.sm.executor.tracking;

import com.vanart.okxnotificationbot.ArithmeticUtils;
import com.vanart.okxnotificationbot.Constants;
import com.vanart.okxnotificationbot.OkxAdapter;
import com.vanart.okxnotificationbot.OkxNotificationsBot;
import com.vanart.okxnotificationbot.dto.BotUpdate;
import com.vanart.okxnotificationbot.dto.Instrument;
import com.vanart.okxnotificationbot.sm.SMEvents;
import com.vanart.okxnotificationbot.sm.SMStates;
import com.vanart.okxnotificationbot.sm.executor.SMActionExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.logging.Logger;

@Component
public class ShowStepsActionExecutor extends SMActionExecutor {
    private static final Logger LOGGER = Logger.getLogger(ShowStepsActionExecutor.class.getName());
    private final OkxNotificationsBot bot;
    private final OkxAdapter okxAdapter;

    @Autowired
    public ShowStepsActionExecutor(OkxNotificationsBot bot, OkxAdapter okxAdapter) {
        this.bot = bot;
        this.okxAdapter = okxAdapter;
    }

    @Override
    public void executeAction(BotUpdate update, StateContext<SMStates, SMEvents> context) {
        var instrument = (Instrument) context.getExtendedState().getVariables().get(Constants.INSTRUMENT);
        var price = okxAdapter.getLastPrice(instrument.getInstId());
        Double step = ArithmeticUtils.countPriceStep(price);
        var stepsButtons = new ArrayList<String>();
        stepsButtons.add(String.valueOf(step / 100));
        stepsButtons.add(String.valueOf(step / 10));
        stepsButtons.add(String.valueOf(step));
        stepsButtons.add(String.valueOf(step * 10));
        bot.showButtons(update.chatId(), "Select the price change step", stepsButtons);
    }
}
