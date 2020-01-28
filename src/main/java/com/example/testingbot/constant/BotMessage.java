package com.example.testingbot.constant;

import java.util.Arrays;
import java.util.List;

import static com.example.testingbot.service.KeyboardService.emoji;

public enum BotMessage {

    GREETING(emoji(":wave:") + " " + "Приветствуем Вас в нашем Боте для тестирования. Для начала зарегистрируйтесь:"),
    REGISTRATION_COMPLETE(emoji(":white_check_mark:") + " " + "Вы зарегистрированы в системе!"),
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
    PROFILE_DONE(emoji(":white_check_mark:") + " " + "Ваша анкета заполнена"),
    CHANGE_PROFILE("Изменить данные?"),
    PROFILE_SAVED(emoji(":white_check_mark:") + " " + "Ваша анкета сохранена"),
    READY(emoji(":question:") + " " + "Поступил новый вопрос, Вы готовы? У вас будет 2 минуты на ответ"),
    NO_QUESTION(emoji(":no_entry:") + " " + "Вопрос еще не готов, ожидайте"),
    QUESTION_TEXT("Что Вы видите на изображении?"),
    QUESTION_FAIL(emoji(":clock2:") + emoji(":exclamation:") + " " + "Время вышло, Ваши ответы: "),
    QUESTION_SUCCESS("Встретимся в "),
    QUESTION_SEND(emoji(":white_check_mark:") + " " + "Ответ отправлен"),
    QUESTION_END("Спасибо за участие! Результаты сообщим позже"),
    ADMIN_MENU("Кабинет администратора"),
    USER_NOT_FOUND("Пользователь не найден"),
    ENTER_ID("Введите ID пользователя:"),
    START_TEST_TIME("Дата начала теста: "),
    FINISH_TEST_TIME("Дата окончания теста: "),
    TEST_NO_START("Тест не начат"),
    TEST_NO_FINISH("Тест не окончен"),
    FIRST_QUESTION("Первый вопрос ожидайте ");

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
