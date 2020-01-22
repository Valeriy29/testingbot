package com.example.testingbot.constant;

public enum UserMessage {

    START("/start"),
    REGISTRATION("Регистрация");

    private String userMessage;

    UserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }
}
