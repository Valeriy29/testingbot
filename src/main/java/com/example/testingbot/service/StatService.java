package com.example.testingbot.service;

import com.example.testingbot.constant.Admin;
import com.example.testingbot.domain.AnswerEntity;
import com.example.testingbot.domain.QuestionEntity;
import com.example.testingbot.domain.UserEntity;
import com.example.testingbot.repository.AnswerRepository;
import com.example.testingbot.repository.QuestionRepository;
import com.example.testingbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StatService {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final MessageService messageService;

    @Autowired
    public StatService(UserRepository userRepository, QuestionRepository questionRepository, AnswerRepository answerRepository, MessageService messageService) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.messageService = messageService;
    }

    public void sendStat(Integer telegramId) {
        UserEntity user = userRepository.findUserEntityByTelegramId(telegramId);

        if (user != null) {
            QuestionEntity questionStart = questionRepository.findByInnerId(user.getUserId(), 0);
            QuestionEntity questionFinish = questionRepository.findByInnerId(user.getUserId(), 9);

            messageService.sendMessageToUser(Integer.valueOf(Admin.ADMIN_ID.getConstant()),
                    "Дата начала теста: " + Optional.ofNullable(questionStart.getTimeAnswer()).map(Date::toString).orElse("no data"),
                    Admin.BASIC_URL.getConstant());
            messageService.sendMessageToUser(Integer.valueOf(Admin.ADMIN_ID.getConstant()),
                    "Дата окончания теста: " + Optional.ofNullable(questionFinish.getTimeAnswer()).map(Date::toString).orElse("no data"),
                    Admin.BASIC_URL.getConstant());

            List<QuestionEntity> questions = questionRepository.findAllByUserId(user.getUserId());
            if (questions.size() > 0) {
                questions.forEach(q -> {
                    List<AnswerEntity> answers = answerRepository.findAllByQuestionId(q.getQuestionId());
                    StringBuilder sb = new StringBuilder();
                    if (answers.size() > 0) {
                        answers.forEach(a -> {
                            sb.append(a.getText()).append("\n");
                        });
                    }
                    messageService.sendMessageToUser(Integer.valueOf(Admin.ADMIN_ID.getConstant()), q.getImage(), Admin.BASIC_URL_PHOTO.getConstant());
                    messageService.sendMessageToUser(Integer.valueOf(Admin.ADMIN_ID.getConstant()), sb.toString(), Admin.BASIC_URL.getConstant());
                });
            }
        }
    }

}
