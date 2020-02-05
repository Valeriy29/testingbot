package com.example.testingbot.controller;

import com.example.testingbot.constant.BotMessage;
import com.example.testingbot.domain.PhotoEntity;
import com.example.testingbot.domain.UserStatus;
import com.example.testingbot.constant.UserMessage;
import com.example.testingbot.domain.UserEntity;
import com.example.testingbot.repository.PhotoRepo;
import com.example.testingbot.service.KeyboardService;
import com.example.testingbot.service.QuestionService;
import com.example.testingbot.service.StatService;
import com.example.testingbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static com.example.testingbot.constant.Admin.*;

@Controller
public class BotController extends TelegramLongPollingBot {

    private final KeyboardService keyboardService;
    private final UserService userService;
    private final QuestionService questionService;
    private final StatService statService;

    private final static String REGEX_ID = "^\\d+$";
    private final static String REGEX_PHONE = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";
    private final static String REGEX_MONTHS = "\\d{1,3}";
    private boolean adminSearchId = false;
    //private boolean adminSearchStat = false;
    //private boolean findImage = false;

    @Autowired
    public BotController(KeyboardService keyboardService, UserService userService, QuestionService questionService, StatService statService) {
        this.keyboardService = keyboardService;
        this.userService = userService;
        this.questionService = questionService;
        this.statService = statService;
    }

//    @Autowired
//    private PhotoRepo photoRepo;

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

//        List<PhotoSize> photoSizeList = message.getPhoto();
//        PhotoEntity entity = new PhotoEntity();
//        entity.setFileId(photoSizeList.get(0).getFileId());
//        photoRepo.save(entity);

        if (message.hasText()) {

            if (!message.getFrom().getId().equals(Integer.valueOf(ADMIN_ID.getConstant())) && !message.getFrom().getId().equals(Integer.valueOf(ADMIN_ID_2.getConstant()))
                    && !message.getFrom().getId().equals(Integer.valueOf(ADMIN_ID_3.getConstant()))) {

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
                            if (String.valueOf(userEntity.getRegistrationStage()).equals(BotMessage.PROFILE_PHONE_STAGE.getBotMessage())) {
                                if (!message.getText().matches(REGEX_PHONE)) {
                                    executeMessage(keyboardService.sendMsg(message, BotMessage.WRONG_PHONE.getBotMessage()));
                                } else {
                                    userService.updateProfile(userEntity, message.getText());
                                }
                            } else if (String.valueOf(userEntity.getRegistrationStage()).equals(BotMessage.PROFILE_CITY_STAGE.getBotMessage())) {
                                if (!UserMessage.getCitiesMessage().contains(message.getText())) {
                                    executeMessage(keyboardService.getCitiesMenu(message, BotMessage.WRONG_CITY.getBotMessage()));
                                } else {
                                    userService.updateProfile(userEntity, message.getText());
                                }
                            } else if (String.valueOf(userEntity.getRegistrationStage()).equals(BotMessage.PROFILE_POSITION_STAGE.getBotMessage())) {
                                if (!UserMessage.getPositionsMessage().contains(message.getText())) {
                                    executeMessage(keyboardService.getPositionsMenu(message, BotMessage.WRONG_POSITION.getBotMessage()));
                                } else {
                                    userService.updateProfile(userEntity, message.getText());
                                }
                            } else if (String.valueOf(userEntity.getRegistrationStage()).equals(BotMessage.PROFILE_EXP_STAGE.getBotMessage())) {
                                if (!message.getText().matches(REGEX_MONTHS)) {
                                    executeMessage(keyboardService.getPositionsMenu(message, BotMessage.WRONG_EXPERIENCE.getBotMessage()));
                                } else {
                                    userService.updateProfile(userEntity, message.getText());
                                }
                            } else {
                                userService.updateProfile(userEntity, message.getText());
                            }
                        }

                        if (userEntity.getStatus().equals(UserStatus.PROFILE)) {
                            if (BotMessage.getConstQuestions().contains(String.valueOf(userEntity.getRegistrationStage()))) {
                                if (String.valueOf(userEntity.getRegistrationStage()).equals(BotMessage.PROFILE_SEX_STAGE.getBotMessage())) {
                                    executeMessage(keyboardService.getProfileSexMenu(message, userService.getProfileQuestion(userEntity)));
                                }
                                if (String.valueOf(userEntity.getRegistrationStage()).equals(BotMessage.PROFILE_MARITAL_STAGE.getBotMessage())) {
                                    executeMessage(keyboardService.getMaritalMenu(message, userService.getProfileQuestion(userEntity)));
                                }
                                if (String.valueOf(userEntity.getRegistrationStage()).equals(BotMessage.PROFILE_PHONE_STAGE.getBotMessage())) {
                                    executeMessage(keyboardService.sendMsg(message, userService.getProfileQuestion(userEntity)));
                                }
                                if (String.valueOf(userEntity.getRegistrationStage()).equals(BotMessage.PROFILE_CITY_STAGE.getBotMessage())) {
                                    executeMessage(keyboardService.getCitiesMenu(message, userService.getProfileQuestion(userEntity)));
                                }
                                if (String.valueOf(userEntity.getRegistrationStage()).equals(BotMessage.PROFILE_POSITION_STAGE.getBotMessage())) {
                                    executeMessage(keyboardService.getPositionsMenu(message, userService.getProfileQuestion(userEntity)));
                                }
                                if (String.valueOf(userEntity.getRegistrationStage()).equals(BotMessage.PROFILE_EXP_STAGE.getBotMessage())) {
                                    executeMessage(keyboardService.sendMsg(message, userService.getProfileQuestion(userEntity)));
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
                                executeMessage(keyboardService.sendMsg(message, BotMessage.PROFILE_SAVED.getBotMessage()));
                                executeMessage(keyboardService.sendMsg(message, BotMessage.FIRST_QUESTION.getBotMessage() + questionService.firstQuestion(userEntity)));
                                executeMessage(keyboardService.getUserInfoMenu(message, BotMessage.DESCRIPTION_TASK.getBotMessage()));

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
                    executeMessage(keyboardService.sendMsg(message, userService.usersInfo()));
                }

                if (message.getText().equals(UserMessage.USER_INFO_BY_TELEGRAM_ID.getUserMessage())) {
                    adminSearchId = true;
                    executeMessage(keyboardService.sendMsg(message, BotMessage.ENTER_ID.getBotMessage()));
                }

                if (message.getText().matches(REGEX_ID) && adminSearchId) {
                    adminSearchId = false;
                    executeMessage(keyboardService.sendMsg(message, userService.userInfoForAdmin(Integer.valueOf(message.getText()))));
                    statService.sendStat(Integer.valueOf(message.getText()));
                }

//                if (message.getText().equals(UserMessage.FIND_DOC.getUserMessage())) {
//                    findImage = true;
//                    executeMessage(keyboardService.sendMsg(message, BotMessage.ENTER_ID_DOC.getBotMessage()));
//                }
//
//                if (findImage && message.getText().matches(REGEX_MONTHS)) {
//                    findImage = false;
//                    statService.sendImage(Long.valueOf(message.getText()));
//                }

//                if (message.getText().equals(UserMessage.USER_ANSWERS.getUserMessage())) {
//                    adminSearchStat = true;
//                    executeMessage(keyboardService.sendMsg(message, BotMessage.ENTER_ID.getBotMessage()));
//                }
//
//                if (message.getText().matches(REGEX_ID) && adminSearchStat) {
//                    adminSearchStat = false;
//                    statService.sendStat(Integer.valueOf(message.getText()));
//                }

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
