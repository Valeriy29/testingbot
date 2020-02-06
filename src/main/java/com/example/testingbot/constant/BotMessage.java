package com.example.testingbot.constant;

import java.util.Arrays;
import java.util.List;

import static com.example.testingbot.service.KeyboardService.emoji;

public enum BotMessage {

    GREETING(emoji(":wave:") + " " + "Здравствуйте. Для начала тестирования необходимо зарегистрироваться."),
    REGISTRATION_COMPLETE(emoji(":white_check_mark:") + " " + "Вы зарегистрированы в системе!"),
    PROFILE("Заполните анкету:"),
    PROFILE_NAME("Введите Ваше имя"),
    PROFILE_LAST_NAME("Введите Вашу фамилию"),
    PROFILE_PATRONYMIC("Введите Ваше отчество"),
    PROFILE_SEX("Укажите Ваш пол"),
    PROFILE_MARITAL("Укажите Ваше семейное положение"),
    PROFILE_PHONE("Укажите телефон в формате\n+79\\*\\* \\*\\*\\* \\*\\* \\*\\*"),
    PROFILE_CITY("Укажите Ваш город"),
    PROFILE_ADDRESS("Адрес точки: Улица, дом"),
    PROFILE_POSITION("Укажите Вашу должность"),
    PROFILE_EXPERIENCE("Сколько месяцев работаете в компании?"),
    PROFILE_ALL_STAGES("11"),
    PROFILE_SEX_STAGE("4"),
    PROFILE_MARITAL_STAGE("5"),
    PROFILE_PHONE_STAGE("6"),
    PROFILE_CITY_STAGE("7"),
    PROFILE_POSITION_STAGE("9"),
    PROFILE_EXP_STAGE("10"),
    PROFILE_DONE(emoji(":white_check_mark:") + " " + "Ваша анкета заполнена"),
    WRONG_PHONE(emoji(":x:") + " " + "Не верный формат номера телефона!"),
    WRONG_CITY(emoji(":x:") + " " + "Не верный город!"),
    WRONG_POSITION(emoji(":x:") + " " + "Не верное название должности!"),
    WRONG_EXPERIENCE(emoji(":x:") + " " + "Не верное количество месяцев стажа!"),
    CHANGE_PROFILE("Изменить данные?"),
    PROFILE_SAVED(emoji(":white_check_mark:") + " " + "Ваша анкета сохранена"),
    READY(emoji(":question:") + " " + "Поступил новый вопрос, Вы готовы? У вас будет 2 минуты на ответ." + "\n" +
            "Что бы приступить нажмите внизу кнопку \"Ответить на вопрос\"" + "\n" +
            emoji(":bangbang:") + " " + "Если кнопку не видно, в поле ввода сообщения нажмите иконку вызвать клавиатуру"),
    NO_QUESTION(emoji(":no_entry:") + " " + "Вопрос еще не готов, ожидайте"),
    QUESTION_TEXT("Напишите нестандартные способы применения этого предмета"),
    QUESTION_FAIL(emoji(":clock2:") + emoji(":exclamation:") + " " + "Этот раунд завершен, я записал такие ваши ответы: "),
    QUESTION_SUCCESS("Встретимся в "),
    QUESTION_SEND(emoji(":white_check_mark:") + " " + "Ответ получен"),
    QUESTION_END("Спасибо за участие! Результаты сообщим позже"),
    ADMIN_MENU("Кабинет администратора"),
    USER_NOT_FOUND("Пользователь не найден"),
    ENTER_ID("Введите ID пользователя:"),
    ENTER_ID_DOC("Введите doc id картинки:"),
    START_TEST_TIME("Дата начала теста: "),
    FINISH_TEST_TIME("Дата окончания теста: "),
    TEST_NO_START("Тест не начат"),
    TEST_NO_FINISH("Тест не окончен"),
    FIRST_QUESTION("Первый вопрос ожидайте "),
    DESCRIPTION_TASK(emoji(":warning:") + " " + "Вам предстоит пройти тест на способность решать задачи нестандартными методами." + "\n" +
            "\n" +
            emoji(":arrow_forward:") + " " + "Победителям будет предложено участие в специальном проекте компании." + "\n" +
            emoji(":arrow_forward:") + " " + "После старта очередного раунда теста, вам будет предъявлена картинка предмета." + "\n" +
            emoji(":arrow_forward:") + " " + "После этого у вас будет ровно 2 минуты, чтобы написать как можно больше нестандартных способов применения этого предмета." + "\n" +
            emoji(":arrow_forward:") + " " + "Каждый ответ необходимо отправить отдельным сообщением."
            + "\n" +
            "Например:" + "\n" +
            emoji(":arrow_forward:") + " " + "Если на картинке изображен «жираф»" + "\n" +
            "К нестандартным способам использования жирафа можно отнести:" + "\n" +
            emoji(":ballot_box_with_check:") + " " + "Играть в баскетбол верхом" + "\n" +
            emoji(":ballot_box_with_check:") + " " + "Залезать в окна второго этажа" + "\n" +
            emoji(":ballot_box_with_check:") + " " + "Научить ходить на двух ногах и наряжать в страуса" + "\n" +
            emoji(":ballot_box_with_check:") + " " + "Подставка для передвижной сотовой станции" + "\n" +
            emoji(":ballot_box_with_check:") + " " + "Переходить водоемы вброд" + "\n" +
            "и т.п." + "\n" +
            emoji(":arrow_forward:") + " " + "Чем больше действительно нестандартных позиций – тем лучше." + "\n" +
            emoji(":arrow_forward:") + " " + "Для чистоты эксперимента будет 5 раундов, с перерывом около 6 часов. Все понятно?" + "\n" +
            "Если нет, то напишите ваш вопрос ответственному сотруднику: @mtstest");

    private String botMessage;

    BotMessage(String botMessage) {
        this.botMessage = botMessage;
    }

    public String getBotMessage() {
        return botMessage;
    }

    public static List<String> getConstQuestions() {
        return Arrays.asList(PROFILE_SEX_STAGE.botMessage, PROFILE_MARITAL_STAGE.botMessage, PROFILE_PHONE_STAGE.botMessage,
                PROFILE_CITY_STAGE.botMessage, PROFILE_POSITION_STAGE.botMessage, PROFILE_EXP_STAGE.botMessage);
    }
}
