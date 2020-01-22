package com.example.testingbot.constant;

public enum BotMessage {

    GREETING("Приветствуем Вас в нашем Боте для тестирования. Для начала зарегистрируйтесь:"),
    REGISTRATION_COMPLETE("Вы зарегистрированы в системе! Введите Ваши данные:");

    private String botMessage;

    BotMessage(String botMessage) {
        this.botMessage = botMessage;
    }

    public String getBotMessage() {
        return botMessage;
    }
}
