package com.vanart.okxnotificationbot.sm;

import com.vanart.okxnotificationbot.Constants;
import com.vanart.okxnotificationbot.dto.BotUpdate;
import org.jetbrains.annotations.NotNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import reactor.core.publisher.Mono;

public class SMUtils {
    @NotNull
    public static Mono<Message<SMEvents>> buildMessageEvent(SMEvents smEvent, BotUpdate event) {
        return Mono.just(MessageBuilder
                .withPayload(smEvent)
                .setHeader(Constants.UPDATE, event)
                .build());
    }

    @NotNull
    public static Mono<Message<SMEvents>> buildButtonClickedEvent(SMEvents event, BotUpdate update) {
        return Mono.just(MessageBuilder
                .withPayload(event)
                .setHeader(Constants.UPDATE, update)
                .build());
    }

    public static void sendEvent(SMEvents sMEvent, BotUpdate botUpdate, StateContext<SMStates, SMEvents> context) {
        context.getStateMachine().sendEvent(SMUtils.buildMessageEvent(sMEvent, botUpdate)).subscribe();
    }

    public static BotUpdate getBotMessage(StateContext<SMStates, SMEvents> context) {
        return (BotUpdate) context.getMessageHeader(Constants.UPDATE);
    }
}
