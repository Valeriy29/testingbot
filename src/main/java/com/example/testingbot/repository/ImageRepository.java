package com.example.testingbot.repository;

import com.example.testingbot.domain.ImageEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends CrudRepository<ImageEntity, Long> {

    @Query(value = "SELECT * FROM images", nativeQuery = true)
    List<ImageEntity> findAllImages();

    @Query(value = "SELECT * FROM images WHERE link = ?1", nativeQuery = true)
    ImageEntity findByImageName(String name);

}
