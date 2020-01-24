package com.example.testingbot.service;

import com.example.testingbot.domain.ImageEntity;
import com.example.testingbot.domain.QuestionEntity;
import com.example.testingbot.domain.UserEntity;
import com.example.testingbot.repository.ImageRepository;
import com.example.testingbot.repository.QuestionRepository;
import com.example.testingbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.example.testingbot.domain.QuestionStatus.*;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    private final static Integer COUNT_OF_IMAGE = 3;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, ImageRepository imageRepository, UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        c.set(Calendar.HOUR_OF_DAY, 12);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        date = c.getTime();

        System.out.println(date);

    }

    public void crateQuestions(Integer telegramId) {
        UserEntity user = userRepository.findUserEntityByTelegramId(telegramId);

        List<ImageEntity> images = imageRepository.findAllImages();
        List<String> imagesForQuestions = new ArrayList<>();

        for (int i = 0; i < COUNT_OF_IMAGE; i++) {
            String link = images.get(new Random().nextInt(images.size()) + 1).getLink();

            if (!imagesForQuestions.contains(link)) {
                imagesForQuestions.add(link);
            }
        }

        Date dateOne = new Date();
        Date dateTwo = new Date();

        AtomicInteger innerId = new AtomicInteger(0);
        List<QuestionEntity> questions = imagesForQuestions.stream()
                .map(image -> {
                    QuestionEntity question = new QuestionEntity();
                    question.setImage(image);
                    question.setUserEntity(user);
                    question.setInnerId(innerId.getAndIncrement());
                    question.setStatus(ACTIVE);

                    if (innerId.get() % 2 != 0) {

                    } else {

                    }

                    return question;
                }).collect(Collectors.toList());

    }

    private Date setDate() {

        return null;
    }
}
