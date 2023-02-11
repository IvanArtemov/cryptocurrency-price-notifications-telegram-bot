package com.vanart.okxnotificationbot.sm.executor.tracking;

import com.vanart.okxnotificationbot.ArithmeticUtils;
import com.vanart.okxnotificationbot.Constants;
import com.vanart.okxnotificationbot.OkxAdapter;
import com.vanart.okxnotificationbot.OkxNotificationsBot;
import com.vanart.okxnotificationbot.dto.BotUpdate;
import com.vanart.okxnotificationbot.dto.Instrument;
import com.vanart.okxnotificationbot.repository.InstrumentRepository;
import com.vanart.okxnotificationbot.sm.SMEvents;
import com.vanart.okxnotificationbot.sm.SMStates;
import com.vanart.okxnotificationbot.sm.SMUtils;
import com.vanart.okxnotificationbot.sm.executor.SMActionExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.logging.Logger;

@Component
public class CheckStepActionExecutor extends SMActionExecutor {
    private static final Logger LOGGER = Logger.getLogger(CheckStepActionExecutor.class.getName());
    private final InstrumentRepository instrumentRepository;
    private final OkxNotificationsBot bot;
    private final OkxAdapter okxAdapter;

    @Autowired
    public CheckStepActionExecutor(InstrumentRepository instrumentRepository, OkxNotificationsBot bot, OkxAdapter okxAdapter) {
        this.instrumentRepository = instrumentRepository;
        this.bot = bot;
        this.okxAdapter = okxAdapter;
    }

    @Override
    public void executeAction(BotUpdate update, StateContext<SMStates, SMEvents> context) {
        Double step = null;
        try {
            var text = Optional.ofNullable(update.data()).orElse("");
            step = Double.parseDouble(text);
        } catch (Exception e) {
            bot.sendMessage(update.chatId(), "Incorrect value of the price step");
            SMUtils.sendEvent(SMEvents.TRACKING_STEP_NOT_SELECTED, update, context);
            return;
        }
        var instrument = (Instrument) context.getExtendedState().getVariables().get(Constants.INSTRUMENT);
        var price = okxAdapter.getLastPrice(instrument.getInstId());
        Double priceStep = ArithmeticUtils.countPriceStep(price);
        if (step < priceStep / 1000) {
            bot.sendMessage(update.chatId(), "The step value is too small");
            SMUtils.sendEvent(SMEvents.TRACKING_STEP_NOT_SELECTED, update, context);
            return;
        }
        context.getStateMachine().getExtendedState().getVariables().put(Constants.PRICE_STEP, step);
        SMUtils.sendEvent(SMEvents.TRACKING_STEP_SELECTED, update, context);
    }
}
