package com.example.testingbot.repository;

import com.example.testingbot.domain.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findUserEntityByTelegramId(Integer telegramId);
}
