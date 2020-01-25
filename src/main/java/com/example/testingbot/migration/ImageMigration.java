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
                new ImageEntity(null, "https://drive.google.com/open?id=128Eb2Rtfqe6qazCatq3LuNGuznElMU1F"),
                new ImageEntity(null, "https://drive.google.com/open?id=1vGCRMJHVl1ksUwlXrwoAeXrU-LWe6xIu"),
                new ImageEntity(null, "https://drive.google.com/open?id=1Y6-GJxXqQrYIC9JRMhIDVlJCWmLwMDkw"),
                new ImageEntity(null, "https://drive.google.com/open?id=1ARzKM_29B-eBKom84KZP5FHnR1iJTSvz"),
                new ImageEntity(null, "https://drive.google.com/open?id=1yytrYeBLz5jt6mCbUFWmn3Sh-5A0T8OT"),
                new ImageEntity(null, "https://drive.google.com/open?id=1vrdMhYQ2FV9pDC62TF7hm2iSRq8j0q7_"),
                new ImageEntity(null, "https://drive.google.com/open?id=1u2I6lGz7fd0-ZZJGoYH08-ev196oESKz"),
                new ImageEntity(null, "https://drive.google.com/open?id=1qJe0zZscy9LF0rCmqyUbtDaD-R9F2yn-"),
                new ImageEntity(null, "https://drive.google.com/open?id=1SFhMjnACqzastcmL7PtEsqirEYQyCCf7"),
                new ImageEntity(null, "https://drive.google.com/open?id=1RnBEyP7dA5A0U7WcQQGGOpFmqZWg3M9I"),
                new ImageEntity(null, "https://drive.google.com/open?id=1JS1bY8nOQS16cvh7mL3sBYd5SVcicphI"),
                new ImageEntity(null, "https://drive.google.com/open?id=1IHWFLOeM1mQR-jwe5sAXBQXoYUVF0VqN"),
                new ImageEntity(null, "https://drive.google.com/open?id=14V7xQOOIOhmjQMJcyyittaAaFq0xBzwV"),
                new ImageEntity(null, "https://drive.google.com/open?id=1Y2cB9HdlEYaFMz4PE0vYDEIla2kUHvT-")
        );
        images.forEach(imageRepository::save);
    }
}
