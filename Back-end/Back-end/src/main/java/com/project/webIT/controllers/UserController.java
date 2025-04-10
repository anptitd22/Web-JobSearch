package com.project.webIT.controllers;

import com.project.webIT.dtos.users.*;
import com.project.webIT.models.User;
import com.project.webIT.response.ResponseObject;
import com.project.webIT.response.users.UserResponse;
import com.project.webIT.services.AuthService;
import com.project.webIT.services.CloudinaryService;
import com.project.webIT.services.UserService;
import com.project.webIT.components.LocalizationUtils;
import com.project.webIT.utils.MessageKeys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result
    ) throws Exception{
        if (result.hasErrors()){
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(
                    ResponseObject.builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .message(String.join(";",errorMessages))
                            .build()
            );
        }
        if (!userDTO.getPassword().equals(userDTO.getRetypePassword())){
            return ResponseEntity.badRequest().body(
                    ResponseObject.builder()
                            .message(localizationUtils.getLocalizedMessage(MessageKeys.PASSWORD_NOT_MATCH))
                            .status(HttpStatus.BAD_REQUEST)
                            .build());
        }
        User newUser = userService.createUser(userDTO);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .message(localizationUtils.getLocalizedMessage(MessageKeys.REGISTER_SUCCESSFULLY,newUser))
                        .data(newUser)
                        .status(HttpStatus.OK)
                        .build());
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(
            @RequestBody UserLoginDTO userLoginDTO,
            HttpServletRequest request
    ) throws  Exception {
        String token = userService.loginUser(userLoginDTO);
        //tra token trong response
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_SUCCESSFULLY))
                        .status(HttpStatus.OK)
                        .data(token).build());
    }

    @PostMapping("/details")
    public ResponseEntity<UserResponse> getUserDetails(
            @RequestHeader("Authorization") String authorizationHeader
    )throws Exception{
        //kiem tra dang nhap va sinh token
        String extractedToken = authorizationHeader.substring(7); //bo Bearer
        User user = userService.getUserDetailsFromToken(extractedToken);
        return ResponseEntity.ok().body(UserResponse.fromUser(user));
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
    public ResponseEntity<?> updateUser(
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
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
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

    @GetMapping("auth/social-login")
    public ResponseEntity<String> socialAuth (
            @RequestParam("login_type") String loginType
    ){
        System.out.println("Received request with login_type: " + loginType);
        loginType = loginType.trim().toLowerCase();
        String url = authService.generateAuthUrl(loginType);
        return ResponseEntity.ok(url);
    }

    @GetMapping("auth/social/callback")
    public ResponseEntity<?>callback (
            @RequestParam("code") String code,
            @RequestParam(name = "login_type", required = false) String loginType,
            HttpServletRequest request
    ) throws Exception {
        System.out.println("Code: " + code + ", LoginType: " + loginType);
        Map<String, Object> userInfo = authService.authenticateAndFetchProfile(code, loginType);
        if(userInfo == null) {
//            return ResponseEntity.badRequest().body(new ResponseObject("Failed to authenticate",
//                    HttpStatus.BAD_REQUEST,null));
            return ResponseEntity.badRequest().body("Failed to authenticate");
        }
        String accountId = "";
        String name = "";
        String picture = "";
        String email = "";

        if (loginType.trim().equals("google")) {
            accountId = (String) Objects.requireNonNullElse(userInfo.get("sub"), "");
            name = (String) Objects.requireNonNullElse(userInfo.get("name"), "");
            picture = (String) Objects.requireNonNullElse(userInfo.get("picture"), "");
            email = (String) Objects.requireNonNullElse(userInfo.get("email"), "");
        } else if (loginType.trim().equals("facebook")) {
//            loginType: "facebook"
            accountId = (String) Objects.requireNonNullElse(userInfo.get("id"), "");
            name = (String) Objects.requireNonNullElse(userInfo.get("name"), "");
            email = (String) Objects.requireNonNullElse(userInfo.get("email"), "");
            // Lấy URL ảnh từ cấu trúc dữ liệu của Facebook
            Object pictureObj = userInfo.get("picture");
            if (pictureObj instanceof Map) {
                Map<?, ?> pictureData = (Map<?, ?>) pictureObj;
                Object dataObj = pictureData.get("data");
                if (dataObj instanceof Map) {
                    Map<?, ?> dataMap = (Map<?, ?>) dataObj;
                    Object urlObj = dataMap.get("url");
                    if (urlObj instanceof String) {
                        picture = (String) urlObj;
                    }
                }
            }
        }
        // Tạo đối tượng UserLoginDTO
        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                .email(email)
                .fullName(name)
                .password("")
                .phoneNumber("")
                .avatar(picture) //profileImage
                .facebookAccountId("")
                .googleAccountId("")
                .roleId((long)1)
                .build();
        if (loginType.trim().equals("google")) {
            userLoginDTO.setGoogleAccountId(accountId);
            //userLoginDTO.setFacebookAccountId("");
        } else if (loginType.trim().equals("facebook")) {
            userLoginDTO.setFacebookAccountId(accountId);
            //userLoginDTO.setGoogleAccountId("");
        }

        return this.login(userLoginDTO,request);
    }
}
