package com.vanart.okxnotificationbot.sm.executor.price;

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

import java.util.logging.Logger;

@Component
public class PriceShowActionExecutor extends SMActionExecutor {
    private static final Logger LOGGER = Logger.getLogger(PriceShowActionExecutor.class.getName());
    private final InstrumentRepository instrumentRepository;
    private final OkxAdapter okxAdapter;
    private final OkxNotificationsBot bot;

    @Autowired
    public PriceShowActionExecutor(InstrumentRepository instrumentRepository, OkxNotificationsBot bot, OkxAdapter okxAdapter) {
        this.instrumentRepository = instrumentRepository;
        this.bot = bot;
        this.okxAdapter = okxAdapter;
    }

    @Override
    public void executeAction(BotUpdate event, StateContext<SMStates, SMEvents> context) {
        var instrument = (Instrument) context.getExtendedState().getVariables().get(Constants.INSTRUMENT);
        var price = okxAdapter.getLastPrice(instrument.getInstId());
        bot.removeButtons(event.chatId(), price.toString());
        SMUtils.sendEvent(SMEvents.PRICE_SHOWED, event, context);
    }
}
