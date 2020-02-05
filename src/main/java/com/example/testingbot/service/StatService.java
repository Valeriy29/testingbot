package com.example.testingbot.service;

import com.example.testingbot.constant.Admin;
import com.example.testingbot.domain.AnswerEntity;
import com.example.testingbot.domain.ImageEntity;
import com.example.testingbot.domain.QuestionEntity;
import com.example.testingbot.domain.UserEntity;
import com.example.testingbot.repository.AnswerRepository;
import com.example.testingbot.repository.ImageRepository;
import com.example.testingbot.repository.QuestionRepository;
import com.example.testingbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.example.testingbot.constant.BotMessage.*;

@Service
public class StatService {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final MessageService messageService;
    private final ImageRepository imageRepository;

    private static final Integer FIRST_INDEX = 0;
    private static final Integer LAST_INDEX = 9;

    @Autowired
    public StatService(UserRepository userRepository, QuestionRepository questionRepository, AnswerRepository answerRepository, MessageService messageService, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.messageService = messageService;
        this.imageRepository = imageRepository;
    }

    public void sendImage(Long imageId) {
        ImageEntity image = imageRepository.findById(imageId).get();
        messageService.sendMessageToUser(Integer.valueOf(Admin.ADMIN_ID_2.getConstant()), image.getLink(), Admin.BASIC_URL_PHOTO.getConstant());
    }


    public void sendStat(Integer telegramId) {
        UserEntity user = userRepository.findUserEntityByTelegramId(telegramId);

        if (user != null) {
            QuestionEntity questionStart = questionRepository.findByInnerId(user.getUserId(), FIRST_INDEX);
            QuestionEntity questionFinish = questionRepository.findByInnerId(user.getUserId(), LAST_INDEX);

            String dateString;
            Calendar calendar = Calendar.getInstance();
            if (questionStart.getTimeAnswer() != null) {
                Date date = questionStart.getTimeAnswer();
                calendar.setTime(date);
                calendar.add(Calendar.HOUR_OF_DAY, 3);
                dateString = calendar.getTime().toString();
            } else {
                dateString = TEST_NO_START.getBotMessage();
            }

            messageService.sendMessageToUser(Integer.valueOf(Admin.ADMIN_ID_2.getConstant()),
                    START_TEST_TIME.getBotMessage() + dateString,
                    Admin.BASIC_URL.getConstant());

            if (questionFinish.getTimeAnswer() != null) {
                Date date = questionFinish.getTimeAnswer();
                calendar.setTime(date);
                calendar.add(Calendar.HOUR_OF_DAY, 3);
                dateString = calendar.getTime().toString();
            } else {
                dateString = TEST_NO_FINISH.getBotMessage();
            }

            messageService.sendMessageToUser(Integer.valueOf(Admin.ADMIN_ID_2.getConstant()),
                    FINISH_TEST_TIME.getBotMessage() + dateString,
                    Admin.BASIC_URL.getConstant());

//            List<QuestionEntity> questions = questionRepository.findAllByUserId(user.getUserId());
//            if (questions.size() > 0) {
//                questions.forEach(q -> {
//                    List<AnswerEntity> answers = answerRepository.findAllByQuestionId(q.getQuestionId());
//                    StringBuilder sb = new StringBuilder();
//                    if (answers.size() > 0) {
//                        answers.forEach(a -> {
//                            sb.append(a.getText()).append("\n");
//                        });
//                    }
//                    messageService.sendMessageToUser(Integer.valueOf(Admin.ADMIN_ID.getConstant()), q.getImage(), Admin.BASIC_URL_PHOTO.getConstant());
//                    messageService.sendMessageToUser(Integer.valueOf(Admin.ADMIN_ID.getConstant()), sb.toString(), Admin.BASIC_URL.getConstant());
//                });
//            }
        }
    }

}
