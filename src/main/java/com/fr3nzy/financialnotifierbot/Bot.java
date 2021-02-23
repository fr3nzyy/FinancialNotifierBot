package com.fr3nzy.financialnotifierbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class Bot extends TelegramLongPollingCommandBot {

    private static final Map<String, String> getenv = System.getenv();
    private static final Logger logger = LoggerFactory.getLogger(Bot.class);

    @Value("${BOT_TOKEN}")
    private String token;

    @Value("${BOT_NAME}")
    private String username;

    @Override
    public void processNonCommandUpdate(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        String userName = getUserName(msg);
        logger.info("userName: {}, chatId: {}", userName, chatId);
        sendMessage(chatId, userName, msg.getText());
    }

    @PostConstruct
    void test() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            TimeUnit.SECONDS.sleep(3);
            this.sendMessage(201664263L, "aleksey_zhukov", "hello");
        }
    }

    public Bot() throws InterruptedException {
        register(new StartCommand("start", "Старт"));
    }

    private String getUserName(Message msg) {
        User user = msg.getFrom();
        String userName = user.getUserName();
        return (userName != null) ? userName : String.format("%s %s", user.getLastName(), user.getFirstName());
    }

    public void sendMessage(Long chatId, String userName, String text) {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            //логируем сбой Telegram Bot API, используя userName
        }
    }


    @Override
    public String getBotUsername() {
        return username;
    }


    @Override
    public String getBotToken() {
        return token;
    }
}
