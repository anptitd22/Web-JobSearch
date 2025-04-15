package com.project.webIT.services;

import com.project.webIT.exceptions.InvalidParamException;
import com.project.webIT.helper.FileHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FileServiceImpl {
    private static final long MAX_IMAGE_SIZE = 10 * 1024 * 1024; // 10MB

    public void validateImageAvatar(List<MultipartFile> files) throws Exception {
        if (files == null || files.isEmpty()) {
            files = new ArrayList<>(); //truong hop khong tai file
        }
        if (files.isEmpty()) {
            throw new InvalidParamException("File is required");
        }

        if (files.size() > 1) {
            throw new InvalidParamException("You can only upload maximum 1 image");
        }

        MultipartFile file = files.get(0);  // get first file

        if (file.getSize() == 0) {
            throw new InvalidParamException("File is empty");
        }

        if (file.getSize() > MAX_IMAGE_SIZE) {
            throw new InvalidParamException("File is too large, max is 10MB");
        }

        if (!FileHelper.isImageFile(file)) {
            throw new InvalidParamException("File must be an image");
        }
    }

    public void validateCV(List<MultipartFile> files) throws Exception {
        if (files == null || files.isEmpty()) {
            files = new ArrayList<>(); //truong hop khong tai file
        }
        if(files.size() > 5){
            throw new InvalidParamException("You can only upload maximum 5 files");
        }
        for (MultipartFile file: files){
            if (file.getSize() == 0) { //truong hop file rong
                throw new InvalidParamException("One or more files are empty");
            }
            if (file.getSize() > 10 * 1024 * 1024) { //kiem tra kich thuoc file anh
                throw new InvalidParamException("File is too large, Maximum is 10MB");
            }
            if (!FileHelper.isValidDocument(file)) {
                throw new InvalidParamException("Invalid file type detected. Only .doc, .docx, and .pdf are allowed");
            };
        }
    }
}
