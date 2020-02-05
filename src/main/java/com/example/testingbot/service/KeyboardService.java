package com.example.testingbot.service;

import com.example.testingbot.constant.BotMessage;
import com.example.testingbot.util.KeyboardFactory;
import com.example.testingbot.util.ParamKeyboard;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;


import java.util.function.Consumer;

import static com.example.testingbot.constant.UserMessage.*;

@Service
public class KeyboardService {

    public static String emoji(String emoji) {
        return EmojiParser.parseToUnicode(emoji);
    }

    public SendMessage getRegistrationMenu(Message message, String text) {
        return getKeyboard(message, text, ParamKeyboard.VERTICAL, false, REGISTRATION.getUserMessage());
    }

    public SendMessage getProfileMenu(Message message, String text) {
        return getKeyboard(message, text, ParamKeyboard.VERTICAL, true, PROFILE.getUserMessage());
    }

    public SendMessage getProfileSexMenu(Message message, String text) {
        return getKeyboard(message, text, ParamKeyboard.VERTICAL, true, MALE.getUserMessage(), FEMALE.getUserMessage());
    }

    public SendMessage getMaritalMenu(Message message, String text) {
        return getKeyboard(message, text, ParamKeyboard.VERTICAL, true, MARRIED.getUserMessage(), SINGLE.getUserMessage());
    }

    public SendMessage getCitiesMenu(Message message, String text) {
        return getKeyboard(message, text, ParamKeyboard.VERTICAL, true, CITY_SOCHI.getUserMessage(), CITY_SAMARA.getUserMessage(), CITY_ORENBURG.getUserMessage());
    }

    public SendMessage getPositionsMenu(Message message, String text) {
        return getKeyboard(message, text, ParamKeyboard.VERTICAL, true, POSITION_MASTER.getUserMessage(), POSITION_SALES.getUserMessage());
    }

    public SendMessage getSaveInfoMenu(Message message, String text) {
        return getKeyboard(message, text, ParamKeyboard.VERTICAL, false, SAVE.getUserMessage(), CHANGE.getUserMessage());
    }

    public SendMessage getUserInfoMenu(Message message, String text) {
        return getKeyboard(message, text, ParamKeyboard.VERTICAL, false, TEST.getUserMessage());
    }

    public SendMessage getAdminMenu(Message message, String text) {
        return getKeyboard(message, text, ParamKeyboard.VERTICAL, false, USERS_INFO.getUserMessage(), USER_INFO_BY_TELEGRAM_ID.getUserMessage(), FIND_DOC.getUserMessage());
    }

    private SendMessage getKeyboard(Message message, String text, ParamKeyboard param, boolean oneTime, String... buttonText) {
        return sendMsgKeyboard(message, text, param, oneTime, buttonText);
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

    private SendMessage sendMsgKeyboard(Message message, String text, ParamKeyboard param, boolean oneTime, String... buttonText) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(KeyboardFactory.buildKeyboard(param, oneTime, sendMessage, buttonText));
        return sendMessage;
    }

}
