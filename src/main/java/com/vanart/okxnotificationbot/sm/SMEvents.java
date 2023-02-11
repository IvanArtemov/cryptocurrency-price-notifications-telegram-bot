package com.vanart.okxnotificationbot.sm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum SMEvents {
    START("/start"),
    MESSAGE_RECEIVED,
    BUTTON_CLICKED,
    TRACKING("/tracking"),
    TRACKING_INSTRUMENT_SELECTED,
    TRACKING_STEP_NOT_SELECTED,
    TRACKING_INSTRUMENT_NOT_SELECTED,
    TRACKING_STEP_SELECTED,
    TRACKING_INSTALLED,
    TRACKING_STEPS_SHOWED,
    PRICE("/price"),
    PRICE_INSTRUMENT_NOT_SELECTED,
    PRICE_INSTRUMENT_SELECTED,
    PRICE_SHOWED,
    LIST("/list"),
    LIST_SHOWED,
    LIST_BACK,
    LIST_REMOVE_ITEM,
    LIST_ITEM_SELECTED,
    REMOVE_BUTTON_CLICKED,
    BACK_BUTTON_CLICKED, CONFIRMATION_NO_CLICKED, CONFIRMATION_YES_CLICKED, LIST_ITEM_REMOVED;

    static private final Map<String, SMEvents> commandEventMap = new HashMap<>();

    static {
        commandEventMap.putAll(
                Arrays
                        .stream(SMEvents.values())
                        .filter(events -> events.command != null)
                        .collect(Collectors.toMap(events -> events.command, events -> events))
        );
    }

    private String command;

    SMEvents() {

    }

    SMEvents(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static Optional<SMEvents> getByCommand(String command) {
        return Optional.ofNullable(commandEventMap.get(command));
    }
}
