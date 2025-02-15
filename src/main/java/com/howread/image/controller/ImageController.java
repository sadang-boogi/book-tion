package com.howread.image.controller;

import com.howread.image.controller.response.ImageResponse;
import com.howread.image.service.ImageService;
import com.howread.image.service.dto.ImageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
@RestController
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "Upload an image")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageResponse> uploadImage(
            @Parameter(description = "이미지 파일 업로드", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestPart final MultipartFile image) {
        final ImageDto imageDto = imageService.save(image);
        ImageResponse response = ImageResponse.from(imageDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
