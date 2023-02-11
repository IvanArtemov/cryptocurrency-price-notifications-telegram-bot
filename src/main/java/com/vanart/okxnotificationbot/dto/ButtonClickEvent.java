package com.vanart.okxnotificationbot.dto;

public class ButtonClickEvent {
    private final Long chatId;

    private final String value;

    public ButtonClickEvent(Long chatId, String value) {
        this.chatId = chatId;
        this.value = value;
    }

    public Long getChatId() {
        return chatId;
    }

    public String getValue() {
        return value;
    }
}
