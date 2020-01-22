package com.example.testingbot.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "answers")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerEntity {

    @Id
    @Column(name = "answer_id")
    @SequenceGenerator( name = "jpaSequence", sequenceName = "ANSWER_SEQUENCE", allocationSize = 1, initialValue = 1 )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jpaSequence")
    private Long answerId;

    @Column(name = "answer_text")
    private String answerText;

    @Column(name = "start_date")
    @CreatedDate
    private Date startDate;

    @Column(name = "finish_date")
    @LastModifiedDate
    private Date finishDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;
}
