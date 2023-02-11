package com.vanart.okxnotificationbot.config;

import com.vanart.okxnotificationbot.sm.SMEvents;
import com.vanart.okxnotificationbot.sm.SMStates;
import com.vanart.okxnotificationbot.sm.executor.ClearStateMachineActionExecutor;
import com.vanart.okxnotificationbot.sm.executor.ShowInstrumentsButtonsActionExecutor;
import com.vanart.okxnotificationbot.sm.executor.confirmation.ConfirmationsActionExecutor;
import com.vanart.okxnotificationbot.sm.executor.confirmation.ConfirmationsButtonClickedActionExecutor;
import com.vanart.okxnotificationbot.sm.executor.list.ItemOptionSelectedActionExecutor;
import com.vanart.okxnotificationbot.sm.executor.list.ItemOptionsActionExecutor;
import com.vanart.okxnotificationbot.sm.executor.list.RemoveItemActionExecutor;
import com.vanart.okxnotificationbot.sm.executor.list.TrackersListShowActionExecutor;
import com.vanart.okxnotificationbot.sm.executor.price.PriceCheckInstrumentsActionExecutor;
import com.vanart.okxnotificationbot.sm.executor.price.PriceShowActionExecutor;
import com.vanart.okxnotificationbot.sm.executor.tracking.CheckInstrumentsActionExecutor;
import com.vanart.okxnotificationbot.sm.executor.tracking.CheckStepActionExecutor;
import com.vanart.okxnotificationbot.sm.executor.tracking.InstallActionExecutor;
import com.vanart.okxnotificationbot.sm.executor.tracking.ShowStepsActionExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachineFactory
public class ChatStateMachineConfig extends StateMachineConfigurerAdapter<SMStates, SMEvents> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StateMachineConfigurerAdapter.class.getName());

    private final ShowInstrumentsButtonsActionExecutor showInstrumentsButtonsExecutor;
    private final CheckInstrumentsActionExecutor checkInstrumentsActionExecutor;
    private final ShowStepsActionExecutor showStepsActionExecutor;
    private final InstallActionExecutor installActionExecutor;
    private final CheckStepActionExecutor checkStepActionExecutor;
    private final ClearStateMachineActionExecutor clearStateMachineActionExecutor;
    private final PriceCheckInstrumentsActionExecutor checkPriceInstrumentsActionExecutor;
    private final PriceShowActionExecutor priceShowActionExecutor;
    private final TrackersListShowActionExecutor trackersListShowActionExecutor;
    private final ItemOptionsActionExecutor itemOptionsActionExecutor;
    private final ItemOptionSelectedActionExecutor itemOptionSelectedActionExecutor;
    private final ConfirmationsActionExecutor confirmationsActionExecutor;
    private final RemoveItemActionExecutor removeItemActionExecutor;
    private final ConfirmationsButtonClickedActionExecutor confirmationsButtonClickedActionExecutor;

    @Autowired
    public ChatStateMachineConfig(ShowInstrumentsButtonsActionExecutor showInstrumentsButtonsActionExecutor, CheckInstrumentsActionExecutor checkInstrumentsActionExecutor, ShowStepsActionExecutor showStepsActionExecutor, InstallActionExecutor installActionExecutor, CheckStepActionExecutor checkStepActionExecutor, ClearStateMachineActionExecutor clearStateMachineActionExecutor, PriceCheckInstrumentsActionExecutor checkPriceInstrumentsActionExecutor, PriceShowActionExecutor priceShowActionExecutor, TrackersListShowActionExecutor trackersListShowActionExecutor, ItemOptionsActionExecutor itemOptionsActionExecutor, ItemOptionSelectedActionExecutor itemOptionSelectedActionExecutor, ConfirmationsActionExecutor confirmationsActionExecutor, RemoveItemActionExecutor removeItemActionExecutor, ConfirmationsButtonClickedActionExecutor confirmationsButtonClickedActionExecutor) {
        this.showInstrumentsButtonsExecutor = showInstrumentsButtonsActionExecutor;
        this.checkInstrumentsActionExecutor = checkInstrumentsActionExecutor;
        this.showStepsActionExecutor = showStepsActionExecutor;
        this.installActionExecutor = installActionExecutor;
        this.checkStepActionExecutor = checkStepActionExecutor;
        this.clearStateMachineActionExecutor = clearStateMachineActionExecutor;
        this.checkPriceInstrumentsActionExecutor = checkPriceInstrumentsActionExecutor;
        this.priceShowActionExecutor = priceShowActionExecutor;
        this.trackersListShowActionExecutor = trackersListShowActionExecutor;
        this.itemOptionsActionExecutor = itemOptionsActionExecutor;
        this.itemOptionSelectedActionExecutor = itemOptionSelectedActionExecutor;
        this.confirmationsActionExecutor = confirmationsActionExecutor;
        this.removeItemActionExecutor = removeItemActionExecutor;
        this.confirmationsButtonClickedActionExecutor = confirmationsButtonClickedActionExecutor;
    }

    @Override
    public void configure(StateMachineStateConfigurer<SMStates, SMEvents> states) throws Exception {

        states
                .withStates()
                .initial(SMStates.WAITING, clearStateMachineActionExecutor)
                .state(SMStates.TRACKING_CHECK_INPUT_INSTRUMENT, checkInstrumentsActionExecutor)
                .state(SMStates.TRACKING_SHOW_INSTRUMENTS, showInstrumentsButtonsExecutor)
                .state(SMStates.TRACKING_SHOW_STEPS, showStepsActionExecutor)
                .state(SMStates.TRACKING_CHECK_STEP, checkStepActionExecutor)
                .state(SMStates.TRACKING_INSTALL, installActionExecutor)
                .state(SMStates.PRICE_SHOW_INSTRUMENTS, showInstrumentsButtonsExecutor)
                .state(SMStates.PRICE_CHECK_INPUT_INSTRUMENTS, checkPriceInstrumentsActionExecutor)
                .state(SMStates.PRICE, priceShowActionExecutor)
                .state(SMStates.LIST, trackersListShowActionExecutor)
                .state(SMStates.LIST_ITEM_SELECTED, itemOptionsActionExecutor)
                .state(SMStates.LIST_ITEM_OPTION_SELECTED, itemOptionSelectedActionExecutor)
                .state(SMStates.LIST_ITEM_REMOVE_CONFIRMATION, confirmationsActionExecutor)
                .state(SMStates.LIST_ITEM_REMOVE_CONFIRMATION_BUTTON_CLICKED, confirmationsButtonClickedActionExecutor)
                .state(SMStates.LIST_ITEM_REMOVE, removeItemActionExecutor)
                .end(SMStates.STOPPED);
    }

    private static void listItemSMCOnfig(StateMachineTransitionConfigurer<SMStates, SMEvents> transitions) throws Exception {
        transitions.withExternal().source(SMStates.WAITING).target(SMStates.LIST).event(SMEvents.LIST)
                .and()
                .withExternal().source(SMStates.LIST).target(SMStates.LIST_ITEM_SELECTED).event(SMEvents.BUTTON_CLICKED)
                .and()
                .withExternal().source(SMStates.LIST_ITEM_SELECTED).target(SMStates.LIST_ITEM_OPTION_SELECTED).event(SMEvents.BUTTON_CLICKED)
                .and()
                .withExternal().source(SMStates.LIST_ITEM_OPTION_SELECTED).target(SMStates.LIST).event(SMEvents.BACK_BUTTON_CLICKED)
                .and()
                .withExternal().source(SMStates.LIST_ITEM_OPTION_SELECTED).target(SMStates.LIST_ITEM_REMOVE_CONFIRMATION).event(SMEvents.REMOVE_BUTTON_CLICKED)
                .and()
                .withExternal().source(SMStates.LIST_ITEM_REMOVE_CONFIRMATION).target(SMStates.LIST_ITEM_REMOVE_CONFIRMATION_BUTTON_CLICKED).event(SMEvents.BUTTON_CLICKED)
                .and()
                .withExternal().source(SMStates.LIST_ITEM_REMOVE_CONFIRMATION_BUTTON_CLICKED).target(SMStates.LIST_ITEM_SELECTED).event(SMEvents.CONFIRMATION_NO_CLICKED)
                .and()
                .withExternal().source(SMStates.LIST_ITEM_REMOVE_CONFIRMATION_BUTTON_CLICKED).target(SMStates.LIST_ITEM_REMOVE).event(SMEvents.CONFIRMATION_YES_CLICKED)
                .and()
                .withExternal().source(SMStates.LIST_ITEM_REMOVE).target(SMStates.LIST).event(SMEvents.LIST_ITEM_REMOVED)
                .and();
    }

    private static void priceSMConfig(StateMachineTransitionConfigurer<SMStates, SMEvents> transitions) throws Exception {
        transitions
                .withExternal().source(SMStates.WAITING).target(SMStates.PRICE_SHOW_INSTRUMENTS).event(SMEvents.PRICE)
                .and()
                .withExternal().source(SMStates.PRICE_SHOW_INSTRUMENTS).target(SMStates.PRICE_CHECK_INPUT_INSTRUMENTS).event(SMEvents.MESSAGE_RECEIVED)
                .and()
                .withExternal().source(SMStates.PRICE_SHOW_INSTRUMENTS).target(SMStates.PRICE_CHECK_INPUT_INSTRUMENTS).event(SMEvents.BUTTON_CLICKED)
                .and()
                .withExternal().source(SMStates.PRICE_CHECK_INPUT_INSTRUMENTS).target(SMStates.PRICE_SHOW_INSTRUMENTS).event(SMEvents.PRICE_INSTRUMENT_NOT_SELECTED)
                .and()
                .withExternal().source(SMStates.PRICE_CHECK_INPUT_INSTRUMENTS).target(SMStates.PRICE).event(SMEvents.PRICE_INSTRUMENT_SELECTED)
                .and()
                .withExternal().source(SMStates.PRICE).target(SMStates.WAITING).event(SMEvents.PRICE_SHOWED)
                .and();
    }

    private static StateMachineTransitionConfigurer<SMStates, SMEvents> trackingSMConfig(StateMachineTransitionConfigurer<SMStates, SMEvents> transitions) throws Exception {
        return transitions
                .withExternal().source(SMStates.WAITING).target(SMStates.TRACKING_SHOW_INSTRUMENTS).event(SMEvents.TRACKING)
                .and()
                .withExternal().source(SMStates.TRACKING_SHOW_INSTRUMENTS).target(SMStates.TRACKING_CHECK_INPUT_INSTRUMENT).event(SMEvents.MESSAGE_RECEIVED)
                .and()
                .withExternal().source(SMStates.TRACKING_SHOW_INSTRUMENTS).target(SMStates.TRACKING_CHECK_INPUT_INSTRUMENT).event(SMEvents.BUTTON_CLICKED)
                .and()
                .withExternal().source(SMStates.TRACKING_CHECK_INPUT_INSTRUMENT).target(SMStates.TRACKING_SHOW_STEPS).event(SMEvents.TRACKING_INSTRUMENT_SELECTED)
                .and()
                .withExternal().source(SMStates.TRACKING_CHECK_INPUT_INSTRUMENT).target(SMStates.TRACKING_SHOW_INSTRUMENTS).event(SMEvents.TRACKING_INSTRUMENT_NOT_SELECTED)
                .and()
                .withExternal().source(SMStates.TRACKING_SHOW_STEPS).target(SMStates.TRACKING_CHECK_STEP).event(SMEvents.MESSAGE_RECEIVED)
                .and()
                .withExternal().source(SMStates.TRACKING_SHOW_STEPS).target(SMStates.TRACKING_CHECK_STEP).event(SMEvents.BUTTON_CLICKED)
                .and()
                .withExternal().source(SMStates.TRACKING_CHECK_STEP).target(SMStates.TRACKING_INSTALL).event(SMEvents.TRACKING_STEP_SELECTED)
                .and()
                .withExternal().source(SMStates.TRACKING_CHECK_STEP).target(SMStates.TRACKING_SHOW_STEPS).event(SMEvents.TRACKING_STEP_NOT_SELECTED)
                .and()
                .withExternal().source(SMStates.TRACKING_INSTALL).target(SMStates.WAITING).event(SMEvents.TRACKING_INSTALLED)
                .and();
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<SMStates, SMEvents> transitions)
            throws Exception {
        trackingSMConfig(transitions);
        priceSMConfig(transitions);
        listItemSMCOnfig(transitions);
    }

}
