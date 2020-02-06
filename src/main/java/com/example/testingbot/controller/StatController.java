package com.example.testingbot.controller;

import com.example.testingbot.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class StatController {

    private StatService statService;

    @Autowired
    public StatController(StatService statService) {
        this.statService = statService;
    }

    @GetMapping(value = "/findAll")
    public File findAllAnswers() {
        return statService.findAllAnswers();
    }
}
