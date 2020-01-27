package com.example.testingbot.repository;

import com.example.testingbot.domain.AnswerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnswerRepository extends CrudRepository<AnswerEntity, Long> {

    @Query(value = "SELECT * FROM answers WHERE question_id = ?1", nativeQuery = true)
    List<AnswerEntity> findAllByQuestionId(Long questionId);
}
