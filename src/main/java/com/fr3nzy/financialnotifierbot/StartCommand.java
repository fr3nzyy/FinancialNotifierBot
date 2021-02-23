package com.fr3nzy.financialnotifierbot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class StartCommand extends BotCommand implements IBotCommand {
    public StartCommand(String identifier, String description) {
        super(identifier, description);
    }


    @Override
    public String getCommandIdentifier() {
        return "start";
    }

    @SneakyThrows
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        //формируем имя пользователя - поскольку userName может быть не заполнено, для этого случая используем имя и фамилию пользователя
        User me = absSender.getMe();
//                String.format("%s %s", user.getLastName(), user.getFirstName());
        //обращаемся к методу суперкласса для отправки пользователю ответа
//        logger.info("userName " + userName);
        SendMessage sendMessage = new SendMessage();
        //включаем поддержку режима разметки, чтобы управлять отображением текста и добавлять эмодзи
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(message.getText() + " start method" );
        absSender.execute(sendMessage);
//        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
//                "Давайте начнём! Если Вам нужна помощь, нажмите /help ");
    }


}
