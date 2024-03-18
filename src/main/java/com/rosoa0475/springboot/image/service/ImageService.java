package com.rosoa0475.springboot.image.service;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rosoa0475.springboot.exception.DataNotFoundException;
import com.rosoa0475.springboot.image.entity.ProfileImage;
import com.rosoa0475.springboot.image.repository.ImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    private final String rootPath = System.getProperty("user.dir");
    private final String imageDir = rootPath+"/image/";

    public ProfileImage uploadImage(MultipartFile imageFile) throws IOException{
        if(imageFile.isEmpty()){
            throw new DataIntegrityViolationException("이미지가 존재하지 않습니다.");
        }
        String originalName = imageFile.getOriginalFilename();
        String uuidName = UUID.randomUUID()+ "."+ getExtension(originalName);
        imageFile.transferTo(new File(getImagePath(uuidName)));
        ProfileImage profileImage = new ProfileImage();
        profileImage.setOriginalName(originalName);
        profileImage.setUuidName(uuidName);
        this.imageRepository.save(profileImage);

        return profileImage;
    }

    private static String getExtension(String fileName){
        int position = fileName.lastIndexOf(".");
        return fileName.substring(position+1);
    }

    private String getImagePath(String filename){
        File file = new File(imageDir);
        if(!file.exists()){
            file.mkdirs();
        }
        return imageDir+filename;
    }

    @SuppressWarnings("null")
    public String getImagePath(Long id){
        Optional<ProfileImage> oimage = this.imageRepository.findById(id);
        if(oimage.isPresent()){
            return getImagePath(oimage.get().getUuidName());
        } else{
            throw new DataNotFoundException("image not found");
        }
    }
}
