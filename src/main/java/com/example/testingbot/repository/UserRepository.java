package com.example.testingbot.repository;

import com.example.testingbot.domain.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findUserEntityByTelegramId(Integer telegramId);

    @Query(value = "SELECT user_id FROM users", nativeQuery = true)
    List<Long> findAllUsersId();

    @Query(value = "SELECT telegram_id FROM users WHERE user_id = ?1", nativeQuery = true)
    Integer findTelegramIdById(Long userId);
}
