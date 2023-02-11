package com.vanart.okxnotificationbot.sm.executor.list;

import com.vanart.okxnotificationbot.Constants;
import com.vanart.okxnotificationbot.dto.BotUpdate;
import com.vanart.okxnotificationbot.repository.TrackersRepository;
import com.vanart.okxnotificationbot.sm.SMEvents;
import com.vanart.okxnotificationbot.sm.SMStates;
import com.vanart.okxnotificationbot.sm.SMUtils;
import com.vanart.okxnotificationbot.sm.executor.SMActionExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class RemoveItemActionExecutor extends SMActionExecutor {
    private static final Logger LOGGER = Logger.getLogger(RemoveItemActionExecutor.class.getName());

    private final TrackersRepository trackersRepository;

    @Autowired
    public RemoveItemActionExecutor(TrackersRepository trackersRepository) {
        this.trackersRepository = trackersRepository;
    }

    @Override
    public void executeAction(BotUpdate update, StateContext<SMStates, SMEvents> context) {
        var trackingId = context.getExtendedState().get(Constants.VALUE, String.class);
        trackersRepository.removeTracking(update.chatId(), Long.parseLong(trackingId));
        SMUtils.sendEvent(SMEvents.LIST_ITEM_REMOVED, update, context);
    }
}
