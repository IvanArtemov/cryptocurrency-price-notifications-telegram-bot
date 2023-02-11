package com.vanart.okxnotificationbot.sm.executor;

import com.vanart.okxnotificationbot.OkxNotificationsBot;
import com.vanart.okxnotificationbot.dto.BotUpdate;
import com.vanart.okxnotificationbot.dto.Instrument;
import com.vanart.okxnotificationbot.repository.InstrumentRepository;
import com.vanart.okxnotificationbot.sm.SMEvents;
import com.vanart.okxnotificationbot.sm.SMStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class ShowInstrumentsButtonsActionExecutor extends SMActionExecutor {
    private static final Logger LOGGER = Logger.getLogger(ShowInstrumentsButtonsActionExecutor.class.getName());
    private final InstrumentRepository instrumentRepository;
    private final OkxNotificationsBot bot;

    @Autowired
    public ShowInstrumentsButtonsActionExecutor(InstrumentRepository instrumentRepository, OkxNotificationsBot bot) {
        this.instrumentRepository = instrumentRepository;
        this.bot = bot;
    }

    @Override
    public void executeAction(BotUpdate update, StateContext<SMStates, SMEvents> context) {
        var text = Optional.ofNullable(update.data()).orElse("");
        List<Instrument> instruments = instrumentRepository.getInstrumentsBySubstring(text);
        if (instruments.isEmpty()) {
            instruments.addAll(instrumentRepository.getInstruments());
        }
        List<String> instrumentsTitles = new ArrayList<>();
        for (var i = 0; i < 4 && i < instruments.size(); i++) {
            instrumentsTitles.add(instruments.get(i).getInstId());
        }
        bot.showButtons(update.chatId(), "Enter the initial characters of the tool name or choose from the suggested ones:", instrumentsTitles);
    }
}
