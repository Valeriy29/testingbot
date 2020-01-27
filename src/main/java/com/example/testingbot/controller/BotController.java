package com.example.testingbot.controller;

import com.example.testingbot.constant.BotMessage;
import com.example.testingbot.domain.UserStatus;
import com.example.testingbot.constant.UserMessage;
import com.example.testingbot.domain.UserEntity;
import com.example.testingbot.service.KeyboardService;
import com.example.testingbot.service.QuestionService;
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
    private final QuestionService questionService;

    @Autowired
    public BotController(KeyboardService keyboardService, UserService userService, QuestionService questionService) {
        this.keyboardService = keyboardService;
        this.userService = userService;
        this.questionService = questionService;
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

            if (!message.getFrom().getId().equals(Integer.valueOf(ADMIN_ID.getConstant()))) {

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

                    if (userEntity.getStatus().equals(UserStatus.REGISTRATION) || userEntity.getStatus().equals(UserStatus.PROFILE)) {

                        if (message.getText().equals(UserMessage.PROFILE.getUserMessage()) && userEntity.getStatus().equals(UserStatus.REGISTRATION)) {
                            userEntity.setStatus(UserStatus.PROFILE);
                            userEntity = userService.updateUser(userEntity);
                        }

                        if (!message.getText().equals(UserMessage.PROFILE.getUserMessage()) && userEntity.getStatus().equals(UserStatus.PROFILE)) {
                            userService.updateProfile(userEntity, message.getText());
                        }

                        if (userEntity.getStatus().equals(UserStatus.PROFILE)) {
                            if (BotMessage.getConstQuestions().contains(String.valueOf(userEntity.getRegistrationStage()))) {
                                if (String.valueOf(userEntity.getRegistrationStage()).equals(BotMessage.PROFILE_SEX_STAGE.getBotMessage())) {
                                    executeMessage(keyboardService.getProfileSexMenu(message, userService.getProfileQuestion(userEntity)));
                                }
                                if (String.valueOf(userEntity.getRegistrationStage()).equals(BotMessage.PROFILE_MARITAL_STAGE.getBotMessage())) {
                                    executeMessage(keyboardService.getMaritalMenu(message, userService.getProfileQuestion(userEntity)));
                                }
                            } else {
                                if (String.valueOf(userEntity.getRegistrationStage()).equals(BotMessage.PROFILE_ALL_STAGES.getBotMessage())) {

                                    executeMessage(keyboardService.sendMsg(message, BotMessage.PROFILE_DONE.getBotMessage()));
                                    executeMessage(keyboardService.sendMsg(message, userService.userInfo(userEntity)));
                                    executeMessage(keyboardService.getSaveInfoMenu(message, BotMessage.CHANGE_PROFILE.getBotMessage()));
                                } else {
                                    executeMessage(keyboardService.sendMsg(message, userService.getProfileQuestion(userEntity)));
                                }
                            }

                            if (message.getText().equals(UserMessage.SAVE.getUserMessage())) {
                                userEntity.setStatus(UserStatus.ACTIVE);

                                questionService.crateQuestions(userEntity.getTelegramId());

                                userService.updateUser(userEntity);
                                executeMessage(keyboardService.getUserInfoMenu(message, BotMessage.PROFILE_SAVED.getBotMessage()));
                            }
                            if (message.getText().equals(UserMessage.CHANGE.getUserMessage())) {
                                userEntity.setStatus(UserStatus.REGISTRATION);
                                userEntity.setRegistrationStage(1);
                                userService.updateUser(userEntity);
                                executeMessage(keyboardService.getProfileMenu(message, BotMessage.PROFILE.getBotMessage()));
                            }
                        }
                    }

                    if (userEntity.getStatus().equals(UserStatus.ACTIVE)) {

                        if (message.getText().equals(UserMessage.USER_INFO.getUserMessage())) {
                            executeMessage(keyboardService.sendMsg(message, userService.userInfo(userEntity)));
                        }

                        if (message.getText().equals(UserMessage.TEST.getUserMessage())) {
                            if (questionService.questIsReady(userEntity)) {
                                questionService.disableReady(userEntity);
                                questionService.sendImageWithText(userEntity);
                            } else {
                                executeMessage(keyboardService.sendMsg(message, BotMessage.NO_QUESTION.getBotMessage()));
                            }
                        }

                        if (!UserMessage.getActiveMessage().contains(message.getText()) && questionService.answeringIsRun(userEntity)) {
                            questionService.answer(userEntity, message.getText());
                            executeMessage(keyboardService.sendMsg(message, BotMessage.QUESTION_SEND.getBotMessage()));
                        }

                    }
                }
                //admin
            } else {

                if (message.getText().equals(UserMessage.START.getUserMessage())) {
                    executeMessage(keyboardService.getAdminMenu(message, BotMessage.ADMIN_MENU.getBotMessage()));
                }

                if (message.getText().equals(UserMessage.USERS_INFO.getUserMessage())) {
                    executeMessage(keyboardService.sendMsg(message, BotMessage.QUESTION_SEND.getBotMessage()));
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
