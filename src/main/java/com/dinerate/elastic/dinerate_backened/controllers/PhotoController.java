package com.dinerate.elastic.dinerate_backened.controllers;

import com.dinerate.elastic.dinerate_backened.Mappers.PhotoMapper;
import com.dinerate.elastic.dinerate_backened.domains.dtos.PhotoDto;
import com.dinerate.elastic.dinerate_backened.domains.entities.Photo;
import com.dinerate.elastic.dinerate_backened.services.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/photos")
public class PhotoController {

    private final PhotoService photoService;
    private final PhotoMapper photoMapper;

    @PostMapping("/upload")
    public PhotoDto createPhoto(@RequestParam("file") MultipartFile file) {
        Photo savedPhoto = photoService.uploadPhoto(file);
        return photoMapper.toDto(savedPhoto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Resource> getPhoto(@PathVariable String id) {
       return  photoService.getPhotoResource(id).map(photo->
                ResponseEntity.ok()
                        .contentType(
                                MediaTypeFactory.getMediaType(photo)
                                        .orElse(MediaType.APPLICATION_OCTET_STREAM)
                        )
                        .header(HttpHeaders.CONTENT_DISPOSITION,"inline")
                        .body(photo))
                .orElse(ResponseEntity.notFound().build());

    }
}
