package com.example.testingbot.service;

import com.example.testingbot.constant.BotMessage;
import com.example.testingbot.util.KeyboardFactory;
import com.example.testingbot.util.ParamKeyboard;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


import java.util.function.Consumer;

import static com.example.testingbot.constant.UserMessage.*;

@Service
public class KeyboardService {

    public static String emoji(String emoji) {
        return EmojiParser.parseToUnicode(emoji);
    }

    public SendMessage getRegistrationMenu(Message message, String text) {
        return getKeyboard(message, text, ParamKeyboard.VERTICAL, REGISTRATION.getUserMessage());
    }

    public SendMessage getProfileMenu(Message message, String text) {
        return getKeyboard(message, text, ParamKeyboard.VERTICAL, PROFILE.getUserMessage());
    }

    public SendMessage getProfileSexMenu(Message message, String text) {
        return getKeyboard(message, text, ParamKeyboard.VERTICAL, MALE.getUserMessage(), FEMALE.getUserMessage());
    }

    public SendMessage getMaritalMenu(Message message, String text) {
        return getKeyboard(message, text, ParamKeyboard.VERTICAL, MARRIED.getUserMessage(), SINGLE.getUserMessage());
    }

    public SendMessage getSaveInfoMenu(Message message, String text) {
        return getKeyboard(message, text, ParamKeyboard.VERTICAL, SAVE.getUserMessage(), CHANGE.getUserMessage());
    }

    public SendMessage getUserInfoMenu(Message message, String text) {
        return getKeyboard(message, text, ParamKeyboard.VERTICAL, TEST.getUserMessage());
    }

    public SendMessage getAdminMenu(Message message, String text) {
        return getKeyboard(message, text, ParamKeyboard.VERTICAL, USERS_INFO.getUserMessage(), USER_INFO_BY_TELEGRAM_ID.getUserMessage(),
                USER_ANSWERS.getUserMessage());
    }

    private SendMessage getKeyboard(Message message, String text, ParamKeyboard param, String... buttonText) {
        return sendMsgKeyboard(message, text, param, buttonText);
    }

    public SendMessage sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        return sendMessage;
    }

    public SendMessage sendInlineMsg(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(String.valueOf(update.getCallbackQuery().getFrom().getId()));
        sendMessage.setText(update.getCallbackQuery().getData());
        return sendMessage;
    }

    private SendMessage sendMsgKeyboard(Message message, String text, ParamKeyboard param, String... buttonText) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(KeyboardFactory.buildKeyboard(param, sendMessage, buttonText));
        return sendMessage;
    }

}
