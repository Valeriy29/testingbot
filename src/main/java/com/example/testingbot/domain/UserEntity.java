package com.example.testingbot.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @Column(name = "user_id")
    @SequenceGenerator( name = "jpaSequence", sequenceName = "USER_SEQUENCE", allocationSize = 1, initialValue = 1 )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jpaSequence")
    private Long userId;

    @Column(name = "telegram_id")
    private Integer telegramId;

    @Column(name = "username")
    private String username;

    @Column(name = "telegram_first_name")
    private String telegramFirstName;

    @Column(name = "telegram_last_name")
    private String telegramLastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "sex")
    private String sex;

    @Column(name = "marital_status")
    private String maritalStatus;

    @Column(name = "phone")
    private String phone;

    @Column(name = "city")
    private String city;

    @Column(name = "store_address")
    private String storeAddress;

    @Column(name = "position")
    private String position;

    @Column(name = "work_experience")
    private String workExperience;

    @Column(name = "answers_count")
    private Integer answersCount;

    @Column(name = "registration_stage")
    private Integer registrationStage;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userEntity", cascade = CascadeType.ALL)
    private Set<QuestionEntity> userQuestion;
}
