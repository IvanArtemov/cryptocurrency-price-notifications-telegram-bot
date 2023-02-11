package com.vanart.okxnotificationbot.sm.executor.list;

import com.vanart.okxnotificationbot.OkxNotificationsBot;
import com.vanart.okxnotificationbot.dto.BotUpdate;
import com.vanart.okxnotificationbot.repository.TrackersRepository;
import com.vanart.okxnotificationbot.sm.SMEvents;
import com.vanart.okxnotificationbot.sm.SMStates;
import com.vanart.okxnotificationbot.sm.executor.SMActionExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class TrackersListShowActionExecutor extends SMActionExecutor {
    private static final Logger LOGGER = Logger.getLogger(TrackersListShowActionExecutor.class.getName());
    private final OkxNotificationsBot bot;
    private final TrackersRepository trackersRepository;

    @Autowired
    public TrackersListShowActionExecutor(OkxNotificationsBot bot, TrackersRepository trackersRepository) {
        this.bot = bot;
        this.trackersRepository = trackersRepository;
    }

    @Override
    public void executeAction(BotUpdate event, StateContext<SMStates, SMEvents> context) {
        var buttons = trackersRepository
                .getByChatId(event.chatId())
                .stream()
                .collect(Collectors.toMap(tracking -> tracking.getInstrument() + " step " + tracking.getStep(), tracking -> tracking.getId().toString()));
        bot.showButtons(event.chatId(), "Trackers: ", buttons);
    }
}
