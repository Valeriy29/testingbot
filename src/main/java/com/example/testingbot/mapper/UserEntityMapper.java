package com.example.testingbot.mapper;

import com.example.testingbot.domain.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.telegram.telegrambots.meta.api.objects.User;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    @Mappings({
            @Mapping(target = "telegramId", source = "id"),
            @Mapping(target = "username", source = "userName"),
            @Mapping(target = "telegramFirstName", source = "firstName"),
            @Mapping(target = "telegramLastName", source = "lastName"),
            @Mapping(target = "answersCount", ignore = true),
            @Mapping(target = "city", ignore = true),
            @Mapping(target = "maritalStatus", ignore = true),
            @Mapping(target = "patronymic", ignore = true),
            @Mapping(target = "phone", ignore = true),
            @Mapping(target = "position", ignore = true),
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "userId", ignore = true),
            @Mapping(target = "workExperience", ignore = true),
            @Mapping(target = "userAnswers", ignore = true),
            @Mapping(target = "storeAddress", ignore = true),
            @Mapping(target = "registrationStage", ignore = true),
            @Mapping(target = "firstName", ignore = true),
            @Mapping(target = "lastName", ignore = true)
    })
    UserEntity mapToUserEntity(User telegramUser);
}
