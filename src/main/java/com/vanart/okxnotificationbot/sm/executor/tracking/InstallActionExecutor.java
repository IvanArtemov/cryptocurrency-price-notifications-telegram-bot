package com.vanart.okxnotificationbot.sm.executor.tracking;

import com.vanart.okxnotificationbot.Constants;
import com.vanart.okxnotificationbot.OkxAdapter;
import com.vanart.okxnotificationbot.OkxNotificationsBot;
import com.vanart.okxnotificationbot.dto.BotUpdate;
import com.vanart.okxnotificationbot.dto.Instrument;
import com.vanart.okxnotificationbot.service.TrackingService;
import com.vanart.okxnotificationbot.sm.SMEvents;
import com.vanart.okxnotificationbot.sm.SMStates;
import com.vanart.okxnotificationbot.sm.SMUtils;
import com.vanart.okxnotificationbot.sm.executor.SMActionExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class InstallActionExecutor extends SMActionExecutor {
    private static final Logger LOGGER = Logger.getLogger(InstallActionExecutor.class.getName());
    private final TrackingService trackingService;
    private final OkxNotificationsBot bot;

    private final OkxAdapter okxAdapter;

    @Autowired
    public InstallActionExecutor(TrackingService trackingService, OkxNotificationsBot bot, OkxAdapter okxAdapter) {
        this.trackingService = trackingService;
        this.bot = bot;
        this.okxAdapter = okxAdapter;
    }


    @Override
    public void executeAction(BotUpdate update, StateContext<SMStates, SMEvents> context) {
        var instrument = (Instrument) context.getExtendedState().getVariables().get(Constants.INSTRUMENT);
        var step = (Double) context.getExtendedState().getVariables().get(Constants.PRICE_STEP);
        var prevPrice = okxAdapter.getLastPrice(instrument.getInstId());
        trackingService.startTracking(update.chatId(), instrument, prevPrice, step);
        bot.removeButtons(update.chatId(), "The tracking for the \"" + (instrument).getInstId() + "\" tool is installed with a step of " + step);
        SMUtils.sendEvent(SMEvents.TRACKING_INSTALLED, update, context);
    }
}
