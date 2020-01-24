package com.example.testingbot;

import com.example.testingbot.controller.BotController;
import com.example.testingbot.migration.ImageMigration;
import com.example.testingbot.repository.ImageRepository;
import com.example.testingbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@SpringBootApplication
public class TestingbotApplication implements CommandLineRunner {

	static {
		ApiContextInitializer.init();
	}

	private final BotController botController;

	@Autowired
	public ImageMigration imageMigration;

	@Autowired
	public TestingbotApplication(BotController botController) {
		this.botController = botController;
	}

	@Bean
	TelegramBotsApi getTelegramBotsApi() {
		return new TelegramBotsApi();
	}

	public static void main(String[] args) {
		SpringApplication.run(TestingbotApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			getTelegramBotsApi().registerBot(botController);
		} catch (TelegramApiRequestException e) {
			e.printStackTrace();
		}
		//imageMigration.migration();
	}
}
