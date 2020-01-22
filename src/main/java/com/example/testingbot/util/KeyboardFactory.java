package com.example.testingbot.util;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class KeyboardFactory {

    private final static ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    private KeyboardFactory() {
    }

    public static ReplyKeyboardMarkup buildKeyboard(ParamKeyboard param, SendMessage sendMessage, String... text) {
        switch (param) {
            case VERTICAL: getVerticalButtons(sendMessage, text);
            case HORIZONTAL: getHorizontalButtons(sendMessage, text);
            default: return null;
        }
    }

    private static ReplyKeyboardMarkup getVerticalButtons(SendMessage sendMessage, String... text) {
        List<KeyboardRow> keyboardRowList = Arrays.stream(text).map(buttonText -> {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(new KeyboardButton(buttonText));
            return keyboardRow;
        }).collect(Collectors.toList());

        return initReplyKeyboard(keyboardRowList, sendMessage);
    }

    private static ReplyKeyboardMarkup getHorizontalButtons(SendMessage sendMessage, String... text) {
        return null;
    }

    private static ReplyKeyboardMarkup initReplyKeyboard(List<KeyboardRow> keyboardRowList, SendMessage sendMessage) {
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        return replyKeyboardMarkup;
    }

}
