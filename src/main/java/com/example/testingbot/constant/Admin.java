package com.example.testingbot.constant;

public enum Admin {

    TOKEN("997367876:AAGFZVw0c81zUQIOrXeKhuUz0MDt6f8roJU"),
    BOT_NAME("company_testing_bot"),
    //    TOKEN("920411958:AAEjN4VyKGZYkoKZalvdIfhD0PROnFGtwL0"),
//    BOT_NAME("dating_zp_bot"),
    //ADMIN_ID("370678219"),
    ADMIN_ID("20419906"),
    ADMIN_ID_2("654323"),
    //ADMIN_ID("11111111"),
    BASIC_URL("https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s"),
    BASIC_URL_PHOTO("https://api.telegram.org/bot%s/sendPhoto?chat_id=%s&photo=%s");

    private String constant;

    Admin(String constant) {
        this.constant = constant;
    }

    public String getConstant() {
        return constant;
    }
}
