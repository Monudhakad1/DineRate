package com.dinerate.elastic.dinerate_backened.services.impl;

import com.dinerate.elastic.dinerate_backened.domains.entities.Photo;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.dinerate.elastic.dinerate_backened.services.PhotoService;
import com.dinerate.elastic.dinerate_backened.services.StorageService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    private final StorageService storageService;
    @Override
    public Photo uploadPhoto(MultipartFile file) {
        String photoId= UUID.randomUUID().toString();
        String url=storageService.store(file,photoId);
        Photo photo=Photo.builder()
                .url(url)
                .uploadedTime(LocalDateTime.now())
                .build();
        return photo;
    }

    @Override
    public Optional<Resource> getPhotoResource(String id) {
        return storageService.loadAsResource(id);
    }
}
