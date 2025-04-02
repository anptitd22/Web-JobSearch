package com.project.webIT.controllers;

import com.project.webIT.dtos.users.*;
import com.project.webIT.models.Company;
import com.project.webIT.models.User;
import com.project.webIT.repositories.UserRepository;
import com.project.webIT.response.auth.LoginResponse;
import com.project.webIT.response.auth.RegisterResponse;
import com.project.webIT.response.companies.CompanyResponse;
import com.project.webIT.response.users.UserResponse;
import com.project.webIT.services.CloudinaryService;
import com.project.webIT.services.UserService;
import com.project.webIT.components.LocalizationUtils;
import com.project.webIT.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final LocalizationUtils localizationUtils;
    private final CloudinaryService cloudinaryService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result
    ){
        try{
            if (result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if (!userDTO.getPassword().equals(userDTO.getRetypePassword())){
                return ResponseEntity.badRequest().body(RegisterResponse.builder()
                        .message(localizationUtils.getLocalizedMessage(MessageKeys.PASSWORD_NOT_MATCH))
                        .build());
            }
            User newUser = userService.createUser(userDTO);
            return ResponseEntity.ok().body(RegisterResponse.builder()
                    .message(localizationUtils.getLocalizedMessage(MessageKeys.REGISTER_SUCCESSFULLY,newUser))
                    .user(newUser)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    localizationUtils.getLocalizedMessage(MessageKeys.REGISTER_FAILED, e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO
    ){
        //kiem tra dang nhap va sinh token
        try {
            String token = userService.login(
                    userLoginDTO.getEmail(),
                    userLoginDTO.getPassword(),
                    userLoginDTO.getRoleId() == null ? 1 : userLoginDTO.getRoleId()
            );
            //tra token trong response
            return ResponseEntity.ok().body(LoginResponse.builder()
                    .message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_SUCCESSFULLY))
                    .token(token).build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(LoginResponse.builder()
                    .message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_FAILED,e.getMessage()))
                    .build());
        }
    }

    @PostMapping("/details")
    public ResponseEntity<UserResponse> getUserDetails(
            @RequestHeader("Authorization") String authorizationHeader
    ){
        //kiem tra dang nhap va sinh token
        try {
            String extractedToken = authorizationHeader.substring(7); //bo Bearer
            User user = userService.getUserDetailsFromToken(extractedToken);
            return ResponseEntity.ok().body(UserResponse.fromUser(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (contentType.endsWith(".jpg") || contentType.endsWith(".png"));
    }

    @PostMapping(value = "uploads/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> postImage(
            @PathVariable("userId") Long userId,
            @RequestPart("files") List<MultipartFile> files
    ){
        try{
            if (files == null || files.isEmpty()) {
                files = new ArrayList<>(); //truong hop khong tai file
            }
            if(files.size() > 1){
                return ResponseEntity.badRequest().body("You can only upload maximum 1 images");
            }
            MultipartFile file = files.getFirst();
            if (file.getSize() == 0) { //truong hop file rong
                return ResponseEntity.badRequest().body("upload fail");
            }
            if (file.getSize() > 10 * 1024 * 1024) { //kiem tra kich thuoc file anh
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).
                        body("File is too large, Maximum is 10MB");
            }
            if (isImageFile(file)) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).
                        body("File must be an image");
            };
            String publicId = userService.getPublicId(userId);
            if (!publicId.isEmpty()){
                return ResponseEntity.ok().body(cloudinaryService.updateImage(publicId,file));
            }
            return ResponseEntity.ok().body(cloudinaryService.upload(file));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("details/{userId}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable ("userId") Long userId,
            @RequestBody UpdateUserDTO updateUserDTO,
            @RequestHeader("Authorization") String authorizationHeader
    ){
        try {
            String extractedToken = authorizationHeader.substring(7); //bo Bearer
            User user = userService.getUserDetailsFromToken(extractedToken);
            //kiem tra xem dung tai khoan khong
            if(!Objects.equals(user.getId(), userId)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            User updateUser = userService.updateUser(userId, updateUserDTO);
            return ResponseEntity.ok().body(UserResponse.fromUser(updateUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("password/{userId}")
    public ResponseEntity<UserResponse> updatePassword(
            @PathVariable ("userId") Long userId,
            @RequestBody PasswordDTO passwordDTO,
            @RequestHeader("Authorization") String authorizationHeader
    ){
        try {
            String extractedToken = authorizationHeader.substring(7); //bo Bearer
            User user = userService.getUserDetailsFromToken(extractedToken);
            //kiem tra xem dung tai khoan khong
            if(!Objects.equals(user.getId(), userId)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            User updateUser = userService.updatePassword(userId, passwordDTO);
            return ResponseEntity.ok().body(UserResponse.fromUser(updateUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("email/{userId}")
    public ResponseEntity<?> updateEmail(
            @PathVariable ("userId") Long userId,
            @RequestBody EmailDTO emailDTO,
            @RequestHeader("Authorization") String authorizationHeader
    ){
        try {
            String extractedToken = authorizationHeader.substring(7); //bo Bearer
            User user = userService.getUserDetailsFromToken(extractedToken);
            //kiem tra xem dung tai khoan khong
            if(!Objects.equals(user.getId(), userId)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            User updateUser = userService.updateEmail(userId, emailDTO);
            //tra token trong response
            return ResponseEntity.ok().body(UserResponse.fromUser(updateUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
