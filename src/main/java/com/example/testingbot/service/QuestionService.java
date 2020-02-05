package com.example.testingbot.service;

import com.example.testingbot.constant.Admin;
import com.example.testingbot.constant.BotMessage;
import com.example.testingbot.domain.*;
import com.example.testingbot.repository.AnswerRepository;
import com.example.testingbot.repository.ImageRepository;
import com.example.testingbot.repository.QuestionRepository;
import com.example.testingbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.example.testingbot.constant.Admin.*;
import static com.example.testingbot.domain.QuestionStatus.*;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private final MessageService messageService;

    private final static Integer COUNT_OF_IMAGE = 10;
    private final static Integer TIME_FIRST_QUESTION = 12;
    private final static Integer TIME_SECOND_QUESTION = 18;
    private final static Integer HOURS_RANGE = 6;
    // test minute
    private final static Integer MINUTE_RANGE = 3;
    private final static String DATE_PATTERN = "HH:mm MM.dd ";

    @Autowired
    public QuestionService(QuestionRepository questionRepository, ImageRepository imageRepository, UserRepository userRepository,
                           AnswerRepository answerRepository, MessageService messageService) {
        this.questionRepository = questionRepository;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
        this.messageService = messageService;
    }

    //@Scheduled(fixedDelay = 3600000)
    //test
    @Scheduled(fixedDelay = 60000)
    public void sendQuestions() {
        List<Long> usersId = userRepository.findAllUsersIdByStatus(ACTIVE.name());

        usersId.forEach(id -> {
            List<QuestionEntity> questionList = questionRepository.findAllByUserId(id);

            questionList.sort(Comparator.comparing(QuestionEntity::getInnerId));
            for (int i = 0; i < questionList.size(); i++) {
                QuestionEntity quest = questionList.get(i);
                if (quest.getStatus().equals(ACTIVE)) {
                    Date newDate = new Date();
                    if (getTime(newDate, Calendar.DATE) == getTime(quest.getTimeQuestion(), Calendar.DATE)
                            && getTime(newDate, Calendar.HOUR_OF_DAY) == getTime(quest.getTimeQuestion(), Calendar.HOUR_OF_DAY)
                            //remove next line (test)
                            && getTime(newDate, Calendar.MINUTE) == getTime(quest.getTimeQuestion(), Calendar.MINUTE)
                            && (quest.getInnerId() == 0 || questionList.get(i - 1).getStatus().equals(PASSIVE))) {
                        Integer telegramId = userRepository.findTelegramIdById(id);
                        messageService.sendMessageToUser(telegramId, BotMessage.READY.getBotMessage(), BASIC_URL.getConstant());
                        quest.setStatus(IN_PROGRESS);
                        quest.setReady(true);
                        questionRepository.save(quest);
                        break;
                    }
                }
            }
        });
    }

    public void sendImageWithText(UserEntity user) {
        List<QuestionEntity> questionsList = questionRepository.searchAllByUserIdAndStatus(user.getUserId(), IN_PROGRESS.name());
        if (questionsList.size() > 0) {
            QuestionEntity question = questionsList.get(0);
            messageService.sendMessageToUser(user.getTelegramId(), question.getImage(), BASIC_URL_PHOTO.getConstant());
            messageService.sendMessageToUser(user.getTelegramId(), BotMessage.QUESTION_TEXT.getBotMessage(), BASIC_URL.getConstant());
            question.setStatus(RUN);
            questionRepository.save(question);
            new AnswerTimer(user, questionRepository, userRepository, answerRepository, messageService).start();
        }
    }

    public String firstQuestion(UserEntity user) {
        List<QuestionEntity> questions = questionRepository.findAllByUserId(user.getUserId());
        questions.sort(Comparator.comparing(QuestionEntity::getInnerId));
        QuestionEntity question = questions.get(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        Date date = question.getTimeQuestion();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, 3);
        return simpleDateFormat.format(calendar.getTime());
    }

    public boolean questIsReady(UserEntity user) {
        List<QuestionEntity> questionsList = questionRepository.searchAllByUserIdAndStatus(user.getUserId(), IN_PROGRESS.name());
        return questionsList.size() > 0 && questionsList.get(0).isReady();
    }

    public void disableReady(UserEntity user) {
        List<QuestionEntity> questionsList = questionRepository.searchAllByUserIdAndStatus(user.getUserId(), IN_PROGRESS.name());
        questionsList.get(0).setReady(false);
        questionRepository.save(questionsList.get(0));
    }

    public boolean answeringIsRun(UserEntity user) {
        return questionRepository.searchAllByUserIdAndStatus(user.getUserId(), RUN.name()).size() > 0;
    }

    public boolean isEndOfTest(UserEntity userEntity) {
        return questionRepository.searchAllByUserIdAndStatus(userEntity.getUserId(), ACTIVE.name()).size() == 0;
    }

    public void answer(UserEntity user, String text) {
        List<QuestionEntity> questionsList = questionRepository.searchAllByUserIdAndStatus(user.getUserId(), RUN.name());

        AnswerEntity answer = new AnswerEntity();
        answer.setQuestionEntity(questionsList.get(0));
        answer.setText(text);
        answerRepository.save(answer);

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

//        dateOne.set(setStartTime(dateOne.get(), TIME_FIRST_QUESTION));
//        dateTwo.set(setStartTime(dateTwo.get(), TIME_SECOND_QUESTION));
        //test
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateOne.get());
        calendar.add(Calendar.MINUTE, MINUTE_RANGE);
        dateOne.set(calendar.getTime());

        AtomicInteger innerId = new AtomicInteger(0);

        imagesForQuestions.stream()
                .map(image -> {
                    QuestionEntity question = new QuestionEntity();
                    question.setImage(image);
                    question.setUserEntity(user);
                    question.setInnerId(innerId.getAndIncrement());
                    question.setStatus(ACTIVE);
                    question.setReady(false);

                    //test
                    question.setTimeQuestion(dateOne.get());
                    Calendar c = Calendar.getInstance();
                    c.setTime(dateOne.get());
                    c.add(Calendar.MINUTE, 3);
                    dateOne.set(c.getTime());

//                    if (innerId.get() % 2 != 0) {
//                        dateOne.set(incrementDay(dateOne.get()));
//                        question.setTimeQuestion(dateOne.get());
//                    } else {
//                        dateTwo.set(incrementDay(dateTwo.get()));
//                        question.setTimeQuestion(dateTwo.get());
//                    }

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

    private static Date dateShift(Date now, Date next) {
        Calendar calendar = Calendar.getInstance();

        if (now.after(next) || now.equals(next)) {
            calendar.setTime(now);
            //calendar.add(Calendar.HOUR_OF_DAY, HOURS_RANGE);
            //test
            calendar.add(Calendar.MINUTE, MINUTE_RANGE);
            return calendar.getTime();
        } else {
            calendar.setTime(now);
            int dayNow = calendar.get(Calendar.DATE);
            int hourNow = calendar.get(Calendar.HOUR_OF_DAY);
            //test
            int minuteNow = calendar.get(Calendar.MINUTE);
            calendar.setTime(next);
            int dayNext = calendar.get(Calendar.DATE);
            int hourNext = calendar.get(Calendar.HOUR_OF_DAY);
            //test
            int minuteNext = calendar.get(Calendar.MINUTE);

            if (dayNow == dayNext && hourNow == hourNext /*test*/) {
                //if (hourNext - hourNow >= HOURS_RANGE) {
                //test
                if (minuteNext - minuteNow >= MINUTE_RANGE) {
                    return next;
                } else {
                    //int difference = hourNext - hourNow;
                    //test
                    int difference = minuteNext - minuteNow;
                    calendar.setTime(next);
                    //calendar.add(Calendar.HOUR_OF_DAY, HOURS_RANGE - difference);
                    //test
                    calendar.add(Calendar.MINUTE, MINUTE_RANGE - difference);
                    return calendar.getTime();
                }
            }
        }
        return next;
    }

    public static class AnswerTimer extends Thread {

        //private final static Integer TIME_TO_ANSWER = 120000;
        //test
        private final static Integer TIME_TO_ANSWER = 60000;
        private final UserEntity user;
        private final QuestionRepository questionRepository;
        private final MessageService messageService;
        private final UserRepository userRepository;
        private final AnswerRepository answerRepository;

        public AnswerTimer(UserEntity user, QuestionRepository questionRepository, UserRepository userRepository, AnswerRepository answerRepository,
                           MessageService messageService) {
            this.user = user;
            this.questionRepository = questionRepository;
            this.messageService = messageService;
            this.userRepository = userRepository;
            this.answerRepository = answerRepository;
        }

        private String getAnswersByQuestion(Long questionId) {
            List<AnswerEntity> answers = answerRepository.findAllByQuestionId(questionId);

            StringBuilder sb = new StringBuilder("\n");
            if (answers.size() > 0) {

                answers.forEach(a -> {
                    sb.append(a.getText()).append("\n");
                });
                return sb.toString();
            }
            return sb.toString();
        }

        @Override
        public void run() {
            try {
                Thread.sleep(TIME_TO_ANSWER);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<QuestionEntity> questionsList = questionRepository.searchAllByUserIdAndStatus(user.getUserId(), RUN.name());
            QuestionEntity question = questionsList.get(0);
            question.setTimeAnswer(new Date());
            question.setStatus(PASSIVE);
            questionRepository.save(question);

            Integer telegramId = user.getTelegramId();

            if (!question.getInnerId().equals(COUNT_OF_IMAGE - 1)) {
                List<QuestionEntity> passiveQuestions = questionRepository.searchAllByUserIdAndStatus(user.getUserId(), PASSIVE.name());
                List<QuestionEntity> activeQuestions = questionRepository.searchAllByUserIdAndStatus(user.getUserId(), ACTIVE.name());
                passiveQuestions.sort(Comparator.comparing(QuestionEntity::getInnerId));
                activeQuestions.sort(Comparator.comparing(QuestionEntity::getInnerId));
                QuestionEntity nowQuestion = passiveQuestions.get(passiveQuestions.size() - 1);
                QuestionEntity nextQuestion = activeQuestions.get(0);
                nextQuestion.setTimeQuestion(dateShift(nowQuestion.getTimeAnswer(), nextQuestion.getTimeQuestion()));
                questionRepository.save(nextQuestion);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
                Date d = nextQuestion.getTimeQuestion();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(d);
                calendar.add(Calendar.HOUR_OF_DAY, 3);
                String date = simpleDateFormat.format(calendar.getTime());

                messageService.sendMessageToUser(telegramId, BotMessage.QUESTION_FAIL.getBotMessage() + getAnswersByQuestion(question.getQuestionId()), BASIC_URL.getConstant());
                messageService.sendMessageToUser(telegramId, BotMessage.QUESTION_SUCCESS.getBotMessage() + date, BASIC_URL.getConstant());
            } else {
                user.setStatus(UserStatus.DONE);
                userRepository.save(user);
                messageService.sendMessageToUser(telegramId, BotMessage.QUESTION_FAIL.getBotMessage() + getAnswersByQuestion(question.getQuestionId()), BASIC_URL.getConstant());
                messageService.sendMessageToUser(telegramId, BotMessage.QUESTION_END.getBotMessage(), BASIC_URL.getConstant());
                questionRepository.save(question);
            }
            interrupt();
        }
    }

}
