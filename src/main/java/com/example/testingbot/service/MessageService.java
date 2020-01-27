package com.example.testingbot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static com.example.testingbot.constant.Admin.BASIC_URL;
import static com.example.testingbot.constant.Admin.TOKEN;

@Service
@Slf4j
public class MessageService {

    private final RestTemplate restTemplate;

    @Autowired
    public MessageService(@Lazy RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendMessageToUser(Integer telegramId, String text, String basicUrl) {
        String url = String.format(basicUrl, TOKEN.getConstant(), telegramId, text);
        try {
            restTemplate.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, String.class).getBody();
            log.info("Message sent {}", text);
        } catch (HttpClientErrorException | ResourceAccessException e) {
            log.info("Error. Message not sent {}", text);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
