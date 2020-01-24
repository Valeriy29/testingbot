package com.example.testingbot.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "images")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageEntity {

    @Id
    @Column(name = "image_id")
    @SequenceGenerator( name = "jpaSequence", sequenceName = "IMAGE_SEQUENCE", allocationSize = 1, initialValue = 1 )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jpaSequence")
    private Long imageId;

    @Column(name = "link")
    private String link;
}
