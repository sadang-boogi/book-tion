package com.rebook.image.reponse;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImagesResponse {

    private String imagePath;

    public static ImagesResponse of(final String imagePath) {
        return new ImagesResponse(imagePath);
    }
}
