package com.example.testingbot.constant;

public enum Admin {

    TOKEN("997367876:AAGFZVw0c81zUQIOrXeKhuUz0MDt6f8roJU"),
    BOT_NAME("company_testing_bot");

    private String constant;

    Admin(String constant) {
        this.constant = constant;
    }

    public String getConstant() {
        return constant;
    }
}
