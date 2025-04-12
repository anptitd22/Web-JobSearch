package com.project.webIT.controllers;

import com.project.webIT.components.LocalizationUtils;
import com.project.webIT.dtos.request.UserCVDTO;
import com.project.webIT.models.UserCV;
import com.project.webIT.dtos.response.ObjectResponse;
import com.project.webIT.dtos.response.UserCVResponse;
import com.project.webIT.services.CloudinaryServiceImpl;
import com.project.webIT.services.FileServiceImpl;
import com.project.webIT.services.UserCVServiceImpl;
import com.project.webIT.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/cv")
@RequiredArgsConstructor
public class UserCVController{
    private final UserServiceImpl userServiceImpl;
    private final UserCVServiceImpl userCVServiceImpl;
    private final LocalizationUtils localizationUtils;
    private final CloudinaryServiceImpl cloudinaryServiceImpl;
    private final FileServiceImpl fileServiceImpl;

    @PostMapping(value = "uploads/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ObjectResponse<List<Map>>> postCV(
            @PathVariable("userId") Long userId,
            @RequestPart("files") List<MultipartFile> files
    ) throws Exception {
        fileServiceImpl.validateCV(files);
        List<Map> uploadResults = new ArrayList<>();
        for (MultipartFile file : files) {
            uploadResults.add(cloudinaryServiceImpl.uploadCV(file));
        }
        return ResponseEntity.ok(
                ObjectResponse.<List<Map>>builder()
                        .status(HttpStatus.OK)
                        .message("Upload CV successfully")
                        .data(uploadResults)
                        .build()
        );
    }

    @PostMapping(value = "{userId}")
    public ResponseEntity<ObjectResponse<UserCVResponse>> addCV(
            @PathVariable("userId") Long userId,
            @RequestBody UserCVDTO userCVDTO
    ) throws Exception {
        UserCV userCV = userCVServiceImpl.createCV(userId, userCVDTO);
        return ResponseEntity.ok(
                ObjectResponse.<UserCVResponse>builder()
                        .status(HttpStatus.OK)
                        .message("Add CV successfully")
                        .data(UserCVResponse.fromUserCV(userCV))
                        .build()
        );
    }

    @GetMapping("users/{userId}")
    public ResponseEntity<ObjectResponse<List<UserCVResponse>>> getUserCVs(
            @PathVariable("userId") Long userId
    ) {
        List<UserCV> userCVList = userCVServiceImpl.getCVs(userId);
        List<UserCVResponse> responseList = userCVList.stream()
                .map(UserCVResponse::fromUserCV)
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                ObjectResponse.<List<UserCVResponse>>builder()
                        .status(HttpStatus.OK)
                        .message("Fetch user CV list successfully")
                        .data(responseList)
                        .build()
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<ObjectResponse<UserCVResponse>> updateUserCV(
            @PathVariable("id") Long id,
            @RequestBody String name
    ) throws Exception {
        UserCV updated = userCVServiceImpl.updateUserCv(id, name);
        return ResponseEntity.ok(
                ObjectResponse.<UserCVResponse>builder()
                        .status(HttpStatus.OK)
                        .message("Rename CV successfully")
                        .data(UserCVResponse.fromUserCV(updated))
                        .build()
        );
    }

    @DeleteMapping("{cvId}")
    public ResponseEntity<ObjectResponse<Map>> deleteCV(
            @PathVariable("cvId") Long cvId
    ) throws Exception {
        String publicIdCV = userCVServiceImpl.getPublicIdCV(cvId);
        Map result = cloudinaryServiceImpl.deleteCV(publicIdCV);
        return ResponseEntity.ok(
                ObjectResponse.<Map>builder()
                        .status(HttpStatus.OK)
                        .message("Delete CV successfully")
                        .data(result)
                        .build()
        );
    }
}
