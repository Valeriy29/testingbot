package com.example.testingbot.constant;

import java.util.Arrays;
import java.util.List;

import static com.example.testingbot.service.KeyboardService.emoji;

public enum UserMessage {

    START("/start"),
    REGISTRATION(emoji(":arrow_forward:") + " " + "Регистрация"),
    PROFILE(emoji(":pencil2:") + " " + "Заполнить анкету"),
    MALE(emoji(":man_office_worker:") + " " + "М"),
    FEMALE(emoji(":woman_office_worker:") + " " + "Ж"),
    MARRIED(emoji(":busts_in_silhouette:") + " " + "Замужем, женат"),
    SINGLE(emoji(":bust_in_silhouette:") + " " + "Не замужем, не женат"),
    CITY_SOCHI("Сочи"),
    CITY_SAMARA("Самара"),
    CITY_ORENBURG("Оренбург"),
    POSITION_SALES("Продавец"),
    POSITION_MASTER("Руководитель"),
    CHANGE(emoji(":recycle:") + " " + "Изменить информацию"),
    SAVE(emoji(":white_check_mark:") + " " + "Сохранить информацию"),
    USER_INFO(emoji(":page_facing_up:") + " " + "Ваша информация"),
    TEST(emoji(":speaking_head_in_silhouette:") + " " + "Ответить на вопрос"),
    USERS_INFO(emoji(":bust_in_silhouette:") + " " + "Все пользователи"),
    USER_INFO_BY_TELEGRAM_ID(emoji(":mag:") + " " + "Найти инфо по Telegram ID"),
    FIND_DOC("Найти картинку по doc id"),
    REPORT("Сформировать отчет"),
    USER_ANSWERS(emoji(":page_with_curl:") + " " + "Все ответы пользователя");


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

    public static List<String> getCitiesMessage() {
        return Arrays.asList(CITY_SOCHI.userMessage, CITY_SAMARA.userMessage, CITY_ORENBURG.userMessage);
    }

    public static List<String> getPositionsMessage() {
        return Arrays.asList(POSITION_SALES.userMessage, POSITION_MASTER.userMessage);
    }
}
