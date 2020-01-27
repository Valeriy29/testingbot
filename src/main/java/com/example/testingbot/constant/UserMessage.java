package com.example.testingbot.constant;

import java.util.Arrays;
import java.util.List;

public enum UserMessage {

    START("/start"),
    REGISTRATION("Регистрация"),
    PROFILE("Заполнить анкету"),
    MALE("М"),
    FEMALE("Ж"),
    MARRIED("Замужем, женат"),
    SINGLE("Не замужем, не женат"),
    CHANGE("Изменить информацию"),
    SAVE("Сохранить информацию"),
    USER_INFO("Ваша информация"),
    TEST("Ответить на вопрос"),
    USERS_INFO("Все пользователи");


    private String userMessage;

    UserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public static List<String> getActiveMessage() {
        return Arrays.asList(USER_INFO.userMessage, TEST.userMessage, SAVE.userMessage, CHANGE.userMessage);
    }
}
