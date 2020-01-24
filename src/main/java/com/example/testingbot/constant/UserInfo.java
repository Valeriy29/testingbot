package com.example.testingbot.constant;

public enum UserInfo {

    INFO("Ваши данные"),
    FIRST_NAME("Имя: "),
    LAST_NAME("Фамилия: "),
    PATRONYMIC("Отчество: "),
    SEX("Пол: "),
    MARITAL_STATUS("Семейное положение: "),
    PHONE("Телефон: "),
    CITY("Город: "),
    ADDRESS_STORE("Адрес точки: "),
    POSITION("Должность: "),
    EXPERIENCE("Стаж в компании: "),
    NEXT_LINE("\n");


    private String userInfo;

    UserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getUserInfo() {
        return userInfo;
    }
}
