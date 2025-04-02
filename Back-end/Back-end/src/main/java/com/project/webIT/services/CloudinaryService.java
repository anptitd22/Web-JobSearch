package com.project.webIT.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public Map upload(MultipartFile file)  {
        if (file==null || file.isEmpty()){
            return new HashMap<>();
        }
        try{
            String fileName = UUID.randomUUID().toString() + "_" + Instant.now().getEpochSecond();
            Map data = this.cloudinary.uploader().upload(file.getBytes(),  ObjectUtils.asMap(
                    "public_id", fileName,
                    "overwrite", true,
                    "resource_type", "image"
            ));
            return data;
        }catch (IOException io){
            throw new RuntimeException("Upload không thành công");
        }
    }
    public Map deleteImage(String publicId) {
        try {
            Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            return result;
        } catch (IOException e) {
            throw new RuntimeException("Xóa ảnh không thành công");
        }
    }
    public Map updateImage(String publicId, MultipartFile file) {
        if(publicId!=null){
            deleteImage(publicId);
        }
        return upload(file);
    }
    ///////uploads docx
    public Map uploadCV(MultipartFile file)  {
        if (file==null || file.isEmpty()){
            return new HashMap<>();
        }
        try{
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = UUID.randomUUID().toString() + "_" + Instant.now().getEpochSecond() + fileExtension;
            Map data = this.cloudinary.uploader().upload(file.getBytes(),  ObjectUtils.asMap(
                    "public_id", fileName,
                    "display_name", file.getOriginalFilename(),
                    "overwrite", true,
                    "resource_type", "raw"
            ));
            return data;
        }catch (IOException io){
            throw new RuntimeException("Upload không thành công");
        }
    }
    public Map deleteCV(String publicId) {
        try {
//            publicId = publicId.substring(0, publicId.lastIndexOf('.'));
            Map options = ObjectUtils.asMap(
                    "resource_type", "raw",  // Quan trọng cho PDF/DOCX
                    "invalidate", true      // Xóa cache CDN
            );
            Map result = cloudinary.uploader().destroy(publicId, options);
            return result;
        } catch (IOException e) {
            throw new RuntimeException("Xóa file không thành công");
        }
    }
    public Map updateCV(String publicId, MultipartFile file) {
        if(publicId!=null){
            deleteCV(publicId);
        }
        return upload(file);
    }
}