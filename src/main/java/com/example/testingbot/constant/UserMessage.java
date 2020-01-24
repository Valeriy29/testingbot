package com.example.testingbot.constant;

public enum UserMessage {

    START("/start"),
    REGISTRATION("Регистрация"),
    PROFILE("Заполнить анкету"),
    MALE("М"),
    FEMALE("Ж");

    private String userMessage;

    UserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }
}
