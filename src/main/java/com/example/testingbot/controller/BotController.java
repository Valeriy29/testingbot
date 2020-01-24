package com.example.testingbot.controller;

import com.example.testingbot.constant.BotMessage;
import com.example.testingbot.constant.Status;
import com.example.testingbot.constant.UserMessage;
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

        Integer userTelegramId;
        if (update.hasCallbackQuery()) {
            userTelegramId = update.getCallbackQuery().getFrom().getId();
        } else {
            userTelegramId = message.getFrom().getId();
        }

        UserEntity userEntity = userService.findUserByTelegramId(userTelegramId);

        if (update.hasCallbackQuery()) {
            executeMessage(keyboardService.sendInlineMsg(update));
        }

        if (message.hasText()) {

            if (!userService.userExists(userEntity)) {

                if (message.getText().equals(UserMessage.START.getUserMessage())) {
                    executeMessage(keyboardService.getRegistrationMenu(message, BotMessage.GREETING.getBotMessage()));
                }

                if (message.getText().equals(UserMessage.REGISTRATION.getUserMessage())) {
                    userService.registration(update);
                    executeMessage(keyboardService.sendMsg(message, BotMessage.REGISTRATION_COMPLETE.getBotMessage()));
                    executeMessage(keyboardService.getProfileMenu(message, BotMessage.PROFILE.getBotMessage()));
                }

            }

            if (userService.userExists(userEntity)) {

                if (userEntity.getStatus().equals(Status.REGISTRATION) || userEntity.getStatus().equals(Status.PROFILE)) {

                    if (message.getText().equals(UserMessage.PROFILE.getUserMessage()) && userEntity.getStatus().equals(Status.REGISTRATION)) {
                        userEntity.setStatus(Status.PROFILE);
                        userEntity = userService.updateUser(userEntity);
                    }

                    if (!message.getText().equals(UserMessage.PROFILE.getUserMessage()) && userEntity.getStatus().equals(Status.PROFILE)) {
                        userService.updateProfile(userEntity, message.getText());
                    }

                    //TODO add keyboard choice marital status, and set status when registration finished
                    if (userEntity.getStatus().equals(Status.PROFILE)) {
                        if (String.valueOf(userEntity.getRegistrationStage()).equals(BotMessage.PROFILE_SEX_STAGE.getBotMessage())) {
                            executeMessage(keyboardService.getProfileSexMenu(message, userService.getProfileQuestion(userEntity)));
                        } else {
                            if (String.valueOf(userEntity.getRegistrationStage()).equals(BotMessage.PROFILE_ALL_STAGES.getBotMessage())) {
                                executeMessage(keyboardService.sendMsg(message, BotMessage.PROFILE_DONE.getBotMessage()));
                            } else {
                                executeMessage(keyboardService.sendMsg(message, userService.getProfileQuestion(userEntity)));
                            }
                        }
                    }
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
