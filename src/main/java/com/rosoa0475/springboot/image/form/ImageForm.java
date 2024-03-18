package com.rosoa0475.springboot.image.form;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageForm {
    private MultipartFile imageFile;
}
