package com.example.testingbot.service;

import com.example.testingbot.constant.BotMessage;
import com.example.testingbot.domain.UserEntity;
import com.example.testingbot.mapper.UserEntityMapper;
import com.example.testingbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static com.example.testingbot.domain.UserStatus.*;
import static com.example.testingbot.constant.UserInfo.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserEntityMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserEntityMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserEntity findUserByTelegramId(Integer telegramId) {
        return userRepository.findUserEntityByTelegramId(telegramId);
    }

    public UserEntity updateUser(UserEntity user) {
        return userRepository.save(user);
    }

    public boolean userExists(UserEntity userEntity) {
        return userEntity != null;
    }

    public void registration(Update update) {
        User telegramUser = update.getMessage().getFrom();
        UserEntity user = userMapper.mapToUserEntity(telegramUser);
        user.setStatus(REGISTRATION);
        user.setRegistrationStage(1);
        userRepository.save(user);
    }

    public String userInfo(UserEntity user) {
        return INFO.getUserInfo() + NEXT_LINE.getUserInfo() +
                FIRST_NAME.getUserInfo() + user.getFirstName() + NEXT_LINE.getUserInfo() +
                LAST_NAME.getUserInfo() + user.getLastName() + NEXT_LINE.getUserInfo() +
                PATRONYMIC.getUserInfo() + user.getPatronymic() + NEXT_LINE.getUserInfo() +
                SEX.getUserInfo() + user.getSex() + NEXT_LINE.getUserInfo() +
                MARITAL_STATUS.getUserInfo() + user.getMaritalStatus() + NEXT_LINE.getUserInfo() +
                PHONE.getUserInfo() + user.getPhone() + NEXT_LINE.getUserInfo() +
                CITY.getUserInfo() + user.getCity() + NEXT_LINE.getUserInfo() +
                ADDRESS_STORE.getUserInfo() + user.getStoreAddress() + NEXT_LINE.getUserInfo() +
                POSITION.getUserInfo() + user.getPosition() + NEXT_LINE.getUserInfo() +
                EXPERIENCE.getUserInfo() + user.getWorkExperience();
    }

    public void updateProfile(UserEntity user, String userData) {
        switch (user.getRegistrationStage()) {
            case 1:
                user.setFirstName(userData);
                break;
            case 2:
                user.setLastName(userData);
                break;
            case 3:
                user.setPatronymic(userData);
                break;
            case 4:
                user.setSex(userData);
                break;
            case 5:
                user.setMaritalStatus(userData);
                break;
            case 6:
                user.setPhone(userData);
                break;
            case 7:
                user.setCity(userData);
                break;
            case 8:
                user.setStoreAddress(userData);
                break;
            case 9:
                user.setPosition(userData);
                break;
            case 10:
                user.setWorkExperience(userData);
                break;
            default:
                break;
        }
        user.setRegistrationStage(user.getRegistrationStage() + 1);
        userRepository.save(user);
    }

    public String getProfileQuestion(UserEntity user) {
        int stage = user.getRegistrationStage();
        switch (stage) {
            case 1:
                return BotMessage.PROFILE_NAME.getBotMessage();
            case 2:
                return BotMessage.PROFILE_LAST_NAME.getBotMessage();
            case 3:
                return BotMessage.PROFILE_PATRONYMIC.getBotMessage();
            case 4:
                return BotMessage.PROFILE_SEX.getBotMessage();
            case 5:
                return BotMessage.PROFILE_MARITAL.getBotMessage();
            case 6:
                return BotMessage.PROFILE_PHONE.getBotMessage();
            case 7:
                return BotMessage.PROFILE_CITY.getBotMessage();
            case 8:
                return BotMessage.PROFILE_ADDRESS.getBotMessage();
            case 9:
                return BotMessage.PROFILE_POSITION.getBotMessage();
            case 10:
                return BotMessage.PROFILE_EXPERIENCE.getBotMessage();
            default:
                return null;
        }
    }

}


