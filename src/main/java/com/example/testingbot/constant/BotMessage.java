package com.example.testingbot.constant;

import java.util.Arrays;
import java.util.List;

public enum BotMessage {

    GREETING("Приветствуем Вас в нашем Боте для тестирования. Для начала зарегистрируйтесь:"),
    REGISTRATION_COMPLETE("Вы зарегистрированы в системе!"),
    PROFILE("Заполните анкету:"),
    PROFILE_NAME("Введите Ваше имя"),
    PROFILE_LAST_NAME("Введите Вашу фамилию"),
    PROFILE_PATRONYMIC("Введите Ваше отчество"),
    PROFILE_SEX("Укажите Ваш пол"),
    PROFILE_MARITAL("Укажите Ваше семейное положение"),
    PROFILE_PHONE("Укажите Ваш номер телефона"),
    PROFILE_CITY("Укажите Ваш город"),
    PROFILE_ADDRESS("Укажите адрес точки"),
    PROFILE_POSITION("Укажите Вашу должность"),
    PROFILE_EXPERIENCE("Укажите Ваш стаж работы в компании (в месяцах)"),
    PROFILE_ALL_STAGES("11"),
    PROFILE_SEX_STAGE("4"),
    PROFILE_MARITAL_STAGE("5"),
    PROFILE_DONE("Ваша анкета заполнена"),
    CHANGE_PROFILE("Изменить данные?"),
    PROFILE_SAVED("Ваша анкета сохранена"),
    READY("Поступил новый вопрос, Вы готовы? У вас будет 2 минуты на ответ"),
    NO_QUESTION("Вопрос еще не готов, ожидайте"),
    QUESTION_TEXT("Что Вы видите на изображении?"),
    QUESTION_FAIL("Время вышло, ваши ответы: "),
    QUESTION_SUCCESS("Встретимся в "),
    QUESTION_SEND("Ответ отправлен"),
    QUESTION_END("Спасибо за участие! Результаты сообщим позже"),
    ADMIN_MENU("Кабинет администратора"),
    USER_NOT_FOUND("Пользователь не найден"),
    ENTER_ID("Введите ID пользователя:");

    private String botMessage;

    BotMessage(String botMessage) {
        this.botMessage = botMessage;
    }

    public String getBotMessage() {
        return botMessage;
    }

    public static List<String> getConstQuestions() {
        return Arrays.asList(PROFILE_SEX_STAGE.botMessage, PROFILE_MARITAL_STAGE.botMessage);
    }
}
