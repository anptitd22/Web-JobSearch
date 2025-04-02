package com.project.webIT.controllers;

import com.project.webIT.components.LocalizationUtils;
import com.project.webIT.dtos.users.UserCVDTO;
import com.project.webIT.models.UserCV;
import com.project.webIT.response.users.UserCVResponse;
import com.project.webIT.services.CloudinaryService;
import com.project.webIT.services.UserCVService;
import com.project.webIT.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/cv")
@RequiredArgsConstructor
public class UserCVController {
    private final UserService userService;
    private final UserCVService userCVService;
    private final LocalizationUtils localizationUtils;
    private final CloudinaryService cloudinaryService;

    private boolean isValidDocument(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (contentType.endsWith((".pdf")) ||
                contentType.endsWith(".doc") || // .doc
                contentType.endsWith(".docx"));
    }

    @PostMapping(value = "uploads/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> postCV(
            @PathVariable("userId") Long userId,
            @RequestPart("files") List<MultipartFile> files
    ){
        try{
            if (files == null || files.isEmpty()) {
                files = new ArrayList<>(); //truong hop khong tai file
            }
            List<Map> uploadResults = new ArrayList<>();
            if(files.size() > 5){
                return ResponseEntity.badRequest().body("You can only upload maximum 5 files");
            }
            if(!userService.checkSizeCV(userId, (long) files.size())){
                return ResponseEntity.badRequest().body("You cannot have than 5 files");
            }
            for (MultipartFile file: files){
                if (file.getSize() == 0) { //truong hop file rong
                    return ResponseEntity.badRequest().body("One or more files are empty");
                }
                if (file.getSize() > 10 * 1024 * 1024) { //kiem tra kich thuoc file anh
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).
                            body("File is too large, Maximum is 10MB");
                }
                if (isValidDocument(file)) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).
                            body("Invalid file type detected. Only .doc, .docx, and .pdf are allowed");
                };
                uploadResults.add(cloudinaryService.uploadCV(file));
            }
            return ResponseEntity.ok().body(uploadResults);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "{userId}")
    public ResponseEntity<?> addCV (
            @PathVariable("userId") Long userId,
            @RequestBody UserCVDTO userCVDTO
    ){
        try{
            UserCV userCV = userCVService.createCV(userId, userCVDTO);
            return ResponseEntity.ok().body(userCV);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("users/{userId}")
    public ResponseEntity<?> addUser (
            @PathVariable("userId") Long userId
    ){
        try{
            List<UserCV> userCVList = userCVService.getCVs(userId);
            return ResponseEntity.ok().body(userCVList.stream()
                    .map(UserCVResponse::fromUserCV)
                    .collect(Collectors.toList()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    @PutMapping("{id}")
    public ResponseEntity<?> updateUserCV (
            @PathVariable("id") Long id,
            @RequestBody String name
    ){
        try{
            return ResponseEntity.ok().body(UserCVResponse.fromUserCV(userCVService.updateUserCv(id, name)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("{cvId}")
    public ResponseEntity<?> deleteCV (
            @PathVariable("cvId") Long cvId
    ){
        try{
            String publicIdCV = userCVService.getPublicIdCV(cvId);
            return ResponseEntity.ok().body(cloudinaryService.deleteCV(publicIdCV));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
