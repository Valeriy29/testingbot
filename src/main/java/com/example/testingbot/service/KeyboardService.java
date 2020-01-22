package com.example.testingbot.service;

import com.example.testingbot.util.KeyboardFactory;
import com.example.testingbot.util.ParamKeyboard;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


import static com.example.testingbot.constant.UserMessage.*;

@Service
public class KeyboardService {

    public static String emoji(String emoji) {
        return EmojiParser.parseToUnicode(emoji);
    }

    public SendMessage getRegistrationMenu(Message message, String text) {
        return getKeyboard(message, text, ParamKeyboard.VERTICAL, REGISTRATION.getUserMessage());
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

    private SendMessage sendMsgKeyboard(Message message, String text, ParamKeyboard param, String... buttonText) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        KeyboardFactory.buildKeyboard(param, sendMessage, buttonText);
        return sendMessage;
    }
}
