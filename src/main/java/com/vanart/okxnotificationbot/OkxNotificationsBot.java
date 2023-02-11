package com.vanart.okxnotificationbot;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.SendMessage;
import com.vanart.okxnotificationbot.dto.BotUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class OkxNotificationsBot {

    private static final Logger LOGGER = Logger.getLogger(OkxNotificationsBot.class.getName());

    private final TelegramBot telegramBot;

    @Autowired
    public OkxNotificationsBot(@Value("${bot.token}") String token) {
        this.telegramBot = new TelegramBot(token);
    }

    public void setEventProcessor(BotEventProcessor processor) {
        telegramBot.setUpdatesListener(updates -> {
            updates.forEach(update -> {
                        BotUpdate botUpdate;
                        if (update.message() != null) {
                            botUpdate = new BotUpdate(update.message().text(), update.message().chat().id());
                            processor.processMessage(botUpdate);
                        } else if (update.callbackQuery() != null) {
                            botUpdate = new BotUpdate(update.callbackQuery().data(), update.callbackQuery().message().chat().id());
                            processor.buttonClick(botUpdate);
                        }
                    }
            );
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    public void sendMessage(Long chatId, String text) {
        telegramBot.execute(new SendMessage(chatId, text));
        LOGGER.info("Sent message: \"" + text + "\" to chatId: " + chatId);
    }

    public void removeButtons(Long chatId, String text) {
        telegramBot.execute(new SendMessage(chatId, text).replyMarkup(new ReplyKeyboardRemove()));
    }

    public void showButtons(Long chatId, String text, List<String> buttonsTitles) {
        showButtons(chatId, text, buttonsTitles.stream().collect(Collectors.toMap(k -> k, v -> v)));
    }

    public void showButtons(Long chatId, String text, Map<String, String> titleValueMap) {
        var replay = new InlineKeyboardMarkup();
        var titles = titleValueMap.keySet().stream().toList();
        for (var i = 0; i < titles.size() / 2 + titles.size() % 2; i++) {
            var buttons = new ArrayList<InlineKeyboardButton>();
            for (var j = i * 2; j < i * 2 + 2 && j < titles.size(); j++) {
                var title = titles.get(j);
                var val = titleValueMap.get(title);
                var button = new InlineKeyboardButton(title).callbackData(val);
                buttons.add(button);
            }
            var arr = new InlineKeyboardButton[buttons.size()];
            replay.addRow(buttons.toArray(arr));
        }
        telegramBot.execute(new SendMessage(chatId, text).replyMarkup(replay));
    }
}
