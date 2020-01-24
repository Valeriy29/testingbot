package com.example.testingbot.migration;

import com.example.testingbot.domain.ImageEntity;
import com.example.testingbot.repository.ImageRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageMigration {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageMigration(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void migration() {
        List<ImageEntity> images = Lists.newArrayList(
                new ImageEntity(null, "https://drive.google.com/open?id=1fzhpuXl7V-ABgsvW6waQucz_OXeSD2RK"),
                new ImageEntity(null, "https://drive.google.com/open?id=1HrvFtIiXD6EcO9pSOkuDO7qAXKVmtweW"),
                new ImageEntity(null, "https://drive.google.com/open?id=128Eb2Rtfqe6qazCatq3LuNGuznElMU1F")
        );
        images.forEach(imageRepository::save);
    }
}
