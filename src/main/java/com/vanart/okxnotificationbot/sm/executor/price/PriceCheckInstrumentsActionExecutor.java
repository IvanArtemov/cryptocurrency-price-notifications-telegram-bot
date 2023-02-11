package com.vanart.okxnotificationbot.sm.executor.price;

import com.vanart.okxnotificationbot.Constants;
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

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class PriceCheckInstrumentsActionExecutor extends SMActionExecutor {
    private static final Logger LOGGER = Logger.getLogger(PriceCheckInstrumentsActionExecutor.class.getName());
    private final InstrumentRepository instrumentRepository;
    private final OkxNotificationsBot bot;

    @Autowired
    public PriceCheckInstrumentsActionExecutor(InstrumentRepository instrumentRepository, OkxNotificationsBot bot) {
        this.instrumentRepository = instrumentRepository;
        this.bot = bot;
    }

    @Override
    public void executeAction(BotUpdate event, StateContext<SMStates, SMEvents> context) {
        var text = Optional.ofNullable(event.data()).orElse("");
        List<Instrument> instruments = instrumentRepository.getInstrumentsBySubstring(text);
        if (instruments.size() != 1) {
            SMUtils.sendEvent(SMEvents.PRICE_INSTRUMENT_NOT_SELECTED, event, context);
        } else {
            context.getExtendedState().getVariables().put(Constants.INSTRUMENT, instruments.get(0));
            SMUtils.sendEvent(SMEvents.PRICE_INSTRUMENT_SELECTED, event, context);
        }
    }
}
