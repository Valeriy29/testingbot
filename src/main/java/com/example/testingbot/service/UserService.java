package com.example.testingbot.service;

import com.example.testingbot.domain.AnswerEntity;
import com.example.testingbot.domain.QuestionEntity;
import com.example.testingbot.domain.UserEntity;
import com.example.testingbot.mapper.UserEntityMapper;
import com.example.testingbot.repository.AnswerRepository;
import com.example.testingbot.repository.QuestionRepository;
import com.example.testingbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.HashSet;
import java.util.Set;

import static com.example.testingbot.constant.Status.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserEntityMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, QuestionRepository questionRepository, AnswerRepository answerRepository, UserEntityMapper userMapper) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.userMapper = userMapper;
    }

    public UserEntity findUserByTelegramId(Integer telegramId) {
        return userRepository.findUserEntityByTelegramId(telegramId);
    }

    public boolean userExists(UserEntity userEntity) {
        return userEntity != null;
    }

    public void registration(Update update) {
        User telegramUser = update.getMessage().getFrom();
        UserEntity user = userMapper.mapToUserEntity(telegramUser);
        user.setStatus(REGISTRATION);
        userRepository.save(user);
    }

    public void updateFirstName(String firstName) {

    }

    public UserEntity simulation() {
        QuestionEntity question = new QuestionEntity();
        question.setImage("http://image.com/images/000000001.jpg");

        questionRepository.save(question);

        UserEntity user = new UserEntity();
        user.setTelegramId(1);
        user.setFirstName("John");
        user.setLastName("Dow");
        user.setPatronymic("Malcolm");
        user.setCity("Atlanta");
        user.setPosition("Manager sales");
        user.setMaritalStatus("married");
        user.setStoreAddress("Atlanta, central building");
        user.setAnswersCount(1);
        user.setWorkExperience(2);
        user.setPhone("6573657893");

        userRepository.save(user);

        QuestionEntity newQuestion = questionRepository.findById(14L).get();

        UserEntity loadUser = userRepository.findById(14L).get();
        AnswerEntity answer = new AnswerEntity();
        answer.setUserEntity(loadUser);
        answer.setQuestion(newQuestion);
        answer.setAnswerText("This is nature");

        Set<AnswerEntity> answers = new HashSet<>();
        answers.add(answer);
        loadUser.setUserAnswers(answers);

        answerRepository.save(answer);
        userRepository.save(loadUser);

        return loadUser;
    }

}
