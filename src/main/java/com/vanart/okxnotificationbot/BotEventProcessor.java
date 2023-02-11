package com.vanart.okxnotificationbot;

import com.vanart.okxnotificationbot.dto.BotUpdate;

public interface BotEventProcessor {
    void processMessage(BotUpdate event);

    void buttonClick(BotUpdate update);
}
