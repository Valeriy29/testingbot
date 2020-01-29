package com.example.testingbot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "photos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoEntity {

    @Id
    @Column(name = "photo_id")
    @SequenceGenerator( name = "jpaSequence", sequenceName = "PHOTO_SEQUENCE", allocationSize = 1, initialValue = 1 )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jpaSequence")
    private Long photoId;

    @Column(name = "file_id")
    private String fileId;
}
