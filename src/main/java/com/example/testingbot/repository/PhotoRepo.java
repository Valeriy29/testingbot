package com.example.testingbot.repository;

import com.example.testingbot.domain.PhotoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface PhotoRepo extends CrudRepository<PhotoEntity, Long> {
}
