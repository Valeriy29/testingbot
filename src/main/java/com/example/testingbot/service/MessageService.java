package com.example.testingbot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static com.example.testingbot.constant.Admin.BASIC_URL;
import static com.example.testingbot.constant.Admin.TOKEN;

@Service
public class MessageService {

    Logger logger = LoggerFactory.getLogger(MessageService.class);

    private final RestTemplate restTemplate;

    @Autowired
    public MessageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendMessageToUser(Integer telegramId, String text) {
        String url = String.format(BASIC_URL.getConstant(), TOKEN.getConstant(), telegramId, text);
        try {
            restTemplate.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, String.class).getBody();
            logger.info("Message sent {}", text);
        } catch (HttpClientErrorException | ResourceAccessException e) {
            logger.info("Error. Message not sent {}", text);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
