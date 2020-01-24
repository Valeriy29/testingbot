package com.example.testingbot.constant;

public enum UserMessage {

    START("/start"),
    REGISTRATION("Регистрация"),
    PROFILE("Заполнить анкету"),
    MALE("М"),
    FEMALE("Ж"),
    MARRIED("Замужем, женат"),
    SINGLE("Не замужем, не женат"),
    CHANGE("Изменить информацию"),
    SAVE("Сохранить информацию");


    private String userMessage;

    UserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }
}
