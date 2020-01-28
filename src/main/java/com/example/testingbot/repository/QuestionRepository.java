package com.example.testingbot.repository;

import com.example.testingbot.domain.QuestionEntity;
import com.example.testingbot.domain.QuestionStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends CrudRepository<QuestionEntity, Long> {

    @Query(value = "SELECT * FROM questions WHERE user_id = ?1", nativeQuery = true)
    List<QuestionEntity> findAllByUserId(Long userId);

    @Query(value = "SELECT * FROM questions WHERE user_id = ?1 AND status = ?2", nativeQuery = true)
    List<QuestionEntity> searchAllByUserIdAndStatus(Long userId, String questionStatus);

    @Query(value = "SELECT * FROM questions WHERE allow_reply = true", nativeQuery = true)
    List<QuestionEntity> searchAllowedQuestion(Long userId);

    @Query(value = "SELECT * FROM questions WHERE user_id = ?1 AND inner_id =?2", nativeQuery = true)
    QuestionEntity findByInnerId(Long userId, Integer innerId);

}
