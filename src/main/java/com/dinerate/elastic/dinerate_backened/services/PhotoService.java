package com.dinerate.elastic.dinerate_backened.services;

import com.dinerate.elastic.dinerate_backened.domains.entities.Photo;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface PhotoService {

    Photo uploadPhoto(MultipartFile file);

    Optional<Resource> getPhotoResource(String id);
}
