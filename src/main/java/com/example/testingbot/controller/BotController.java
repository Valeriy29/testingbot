package com.example.testingbot.controller;

import com.example.testingbot.constant.Status;
import com.example.testingbot.domain.UserEntity;
import com.example.testingbot.service.KeyboardService;
import com.example.testingbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.example.testingbot.constant.Admin.*;
import static com.example.testingbot.constant.BotMessage.*;
import static com.example.testingbot.constant.Status.*;
import static com.example.testingbot.constant.UserMessage.*;
import static com.example.testingbot.constant.UserMessage.REGISTRATION;

@Controller
public class BotController extends TelegramLongPollingBot {

    private final KeyboardService keyboardService;
    private final UserService userService;

    @Autowired
    public BotController(KeyboardService keyboardService, UserService userService) {
        this.keyboardService = keyboardService;
        this.userService = userService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        UserEntity userEntity = userService.findUserByTelegramId(message.getFrom().getId());

        if (message.hasText()) {

            if (!userService.userExists(userEntity)) {

                if (message.getText().equals(START.getUserMessage())) {
                    executeMessage(keyboardService.getRegistrationMenu(message, GREETING.getBotMessage()));
                }

                if (message.getText().equals(REGISTRATION.getUserMessage())) {
                    userService.registration(update);
                    executeMessage(keyboardService.sendMsg(message, REGISTRATION_COMPLETE.getBotMessage()));
                }

            }

            if (userService.userExists(userEntity)) {

                if (userEntity.getStatus().equals(Status.REGISTRATION)) {

                }

            }

        }
    }

    private void executeMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME.getConstant();
    }

    @Override
    public String getBotToken() {
        return TOKEN.getConstant();
    }
}
