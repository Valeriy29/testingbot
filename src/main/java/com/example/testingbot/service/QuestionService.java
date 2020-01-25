package com.example.testingbot.service;

import com.example.testingbot.constant.AnswerStatus;
import com.example.testingbot.constant.BotMessage;
import com.example.testingbot.domain.ImageEntity;
import com.example.testingbot.domain.QuestionEntity;
import com.example.testingbot.domain.UserEntity;
import com.example.testingbot.repository.ImageRepository;
import com.example.testingbot.repository.QuestionRepository;
import com.example.testingbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.example.testingbot.domain.QuestionStatus.*;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final MessageService messageService;

    private final static Integer COUNT_OF_IMAGE = 10;
    private final static Integer TIME_FIRST_QUESTION = 12;
    private final static Integer TIME_SECOND_QUESTION = 18;
    private final static Integer HOURS_RANGE = 6;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, ImageRepository imageRepository, UserRepository userRepository, MessageService messageService) {
        this.questionRepository = questionRepository;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.messageService = messageService;
    }

    //    @PostConstruct
//    public void post() {
//        UserEntity user = userRepository.findById(1L).get();
//
//        crateQuestions(user.getTelegramId());
//    }

    @Scheduled(fixedDelay = 3600000)
    public void sendQuestions() {
        String text = BotMessage.QUESTION_TEXT.getBotMessage();

        List<Long> usersId = userRepository.findAllUsersId();

        usersId.forEach(id -> {
            List<QuestionEntity> questionList = questionRepository.findAllByUserId(id);

            questionList.sort(Comparator.comparing(QuestionEntity::getInnerId));

            for (int i = 0; i < questionList.size(); i++) {
                QuestionEntity quest = questionList.get(i);
                if (quest.getStatus().equals(ACTIVE)) {

                    if (getTime(new Date(), Calendar.DATE) == getTime(quest.getTimeQuestion(), Calendar.DATE)
                            && getTime(new Date(), Calendar.HOUR_OF_DAY) == getTime(quest.getTimeQuestion(), Calendar.HOUR_OF_DAY)
                            && (quest.getInnerId() == 0 || questionList.get(i - 1).getStatus().equals(PASSIVE))) {
                        Integer telegramId = userRepository.findTelegramIdById(id);
                        messageService.sendMessageToUser(telegramId, quest.getImage());
                        messageService.sendMessageToUser(telegramId, text);
                        quest.setStatus(IN_PROGRESS);
                        break;
                    }
                }
            }
        });
    }

    public AnswerStatus answer(Integer telegramId, String text) {
        UserEntity user = userRepository.findUserEntityByTelegramId(telegramId);
        QuestionEntity question = questionRepository.findByUserIdAndStatus(user.getUserId(), IN_PROGRESS);

        if (question != null) {
            question.setAnswerText(text);
            question.setStatus(PASSIVE);
            question.setTimeAnswer(new Date());
            questionRepository.save(question);
            QuestionEntity nextQuestion = questionRepository.findByInnerId(question.getInnerId() + 1);
            if (nextQuestion != null) {
                nextQuestion.setTimeQuestion(dateShift(question.getTimeAnswer(), nextQuestion.getTimeQuestion()));
            }
            return AnswerStatus.SUCCESS;
        }

        List<QuestionEntity> passiveQuestions = questionRepository.findAllByUserIdAndStatus(user.getUserId(), PASSIVE);
        List<QuestionEntity> activeQuestions = questionRepository.findAllByUserIdAndStatus(user.getUserId(), ACTIVE);
        passiveQuestions.sort(Comparator.comparing(QuestionEntity::getInnerId));
        activeQuestions.sort(Comparator.comparing(QuestionEntity::getInnerId));
        QuestionEntity nowQuestion = passiveQuestions.get(passiveQuestions.size() - 1);
        QuestionEntity nextQuestion = passiveQuestions.get(0);
        nextQuestion.setTimeQuestion(dateShift(nowQuestion.getTimeAnswer(), nextQuestion.getTimeQuestion()));
        return AnswerStatus.FAIL;
    }

    public void crateQuestions(Integer telegramId) {
        UserEntity user = userRepository.findUserEntityByTelegramId(telegramId);

        List<ImageEntity> images = imageRepository.findAllImages();
        List<String> imagesForQuestions = new ArrayList<>();

        int count = 0;
        while (count != COUNT_OF_IMAGE) {
            String link = images.get(new Random().nextInt(images.size())).getLink();

            if (!imagesForQuestions.contains(link)) {
                imagesForQuestions.add(link);
                count++;
            }
        }

        AtomicReference<Date> dateOne = new AtomicReference<>(new Date());
        AtomicReference<Date> dateTwo = new AtomicReference<>(new Date());

        dateOne.set(setStartTime(dateOne.get(), TIME_FIRST_QUESTION));
        dateTwo.set(setStartTime(dateTwo.get(), TIME_SECOND_QUESTION));

        AtomicInteger innerId = new AtomicInteger(0);

        imagesForQuestions.stream()
                .map(image -> {
                    QuestionEntity question = new QuestionEntity();
                    question.setImage(image);
                    question.setUserEntity(user);
                    question.setInnerId(innerId.getAndIncrement());
                    question.setStatus(ACTIVE);

                    if (innerId.get() % 2 != 0) {
                        dateOne.set(incrementDay(dateOne.get()));
                        question.setTimeQuestion(dateOne.get());
                    } else {
                        dateTwo.set(incrementDay(dateTwo.get()));
                        question.setTimeQuestion(dateTwo.get());
                    }

                    return question;
                }).collect(Collectors.toList())
                .forEach(questionRepository::save);
    }

    private Date setStartTime(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private Date incrementDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    private int getTime(Date date, int paramTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(paramTime);
    }

    private Date dateShift(Date now, Date next) {
        Calendar calendar = Calendar.getInstance();

        if (now.after(next) || now.equals(next)) {
            calendar.setTime(now);
            calendar.add(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + HOURS_RANGE);
            return calendar.getTime();
        } else {
            calendar.setTime(now);
            int dayNow = calendar.get(Calendar.DATE);
            int hourNow = calendar.get(Calendar.HOUR_OF_DAY);
            calendar.setTime(next);
            int dayNext = calendar.get(Calendar.DATE);
            int hourNext = calendar.get(Calendar.HOUR_OF_DAY);

            if (dayNow == dayNext) {
                if (hourNext - hourNow >= HOURS_RANGE || hourNext - hourNow == HOURS_RANGE) {
                    return next;
                } else {
                    int difference = hourNext - hourNow;
                    calendar.setTime(next);
                    calendar.add(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + difference);
                    return calendar.getTime();
                }
            }
        }
        return next;
    }

    public static class AnswerTimer extends Thread {

        private final static Integer TIME_TO_ANSWER = 120000;
        private Long userId;
        private QuestionRepository questionRepository;

        public AnswerTimer(Long userId, QuestionRepository questionRepository) {
            this.userId = userId;
            this.questionRepository = questionRepository;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(TIME_TO_ANSWER);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            QuestionEntity question = questionRepository.findByUserIdAndStatus(userId, IN_PROGRESS);
            if (question != null) {
                question.setStatus(PASSIVE);
                question.setAnswerText("");
                question.setTimeAnswer(new Date());
            }
        }
    }

}
