package com.example.testingbot.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionEntity {

    @Id
    @Column(name = "question_id")
    @SequenceGenerator( name = "jpaSequence", sequenceName = "QUESTION_SEQUENCE", allocationSize = 1, initialValue = 1 )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jpaSequence")
    private Long questionId;

    @Column(name = "innerId")
    private Integer innerId;

    @Column(name = "time_question")
    private Date timeQuestion;

    @Column(name = "time_answer")
    private Date timeAnswer;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private QuestionStatus status;

    @Column(name = "image")
    private String image;

    @Column(name = "answer_text")
    private String answerText;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;
}
