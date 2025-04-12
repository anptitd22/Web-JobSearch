package com.project.webIT.controllers;

import com.project.webIT.dtos.request.*;
import com.project.webIT.helper.ValidationHelper;
import com.project.webIT.models.User;
import com.project.webIT.dtos.response.ObjectResponse;
import com.project.webIT.dtos.response.UserResponse;
import com.project.webIT.services.AuthServiceImpl;
import com.project.webIT.services.CloudinaryServiceImpl;
import com.project.webIT.services.FileServiceImpl;
import com.project.webIT.services.UserServiceImpl;
import com.project.webIT.components.LocalizationUtils;
import com.project.webIT.utils.MessageKeys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController{
    private final UserServiceImpl userServiceImpl;
    private final LocalizationUtils localizationUtils;
    private final CloudinaryServiceImpl cloudinaryServiceImpl;
    private final AuthServiceImpl authServiceImpl;
    private final FileServiceImpl fileServiceImpl;

    @PostMapping("register")
    public ResponseEntity<ObjectResponse<?>> createUser(
            @Valid UserDTO userDTO,
            BindingResult result
    ) throws Exception {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    ObjectResponse.<Void>builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .message(ValidationHelper.extractDetailedErrorMessages(result))
                            .build()
            );
        }
        User newUser = userServiceImpl.createUser(userDTO);
        return ResponseEntity.ok(
                ObjectResponse.<Void>builder()
                        .status(HttpStatus.OK)
                        .message(localizationUtils.getLocalizedMessage(MessageKeys.REGISTER_SUCCESSFULLY, newUser))
                        .build()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ObjectResponse<String>> login(
            @RequestBody UserLoginDTO userLoginDTO,
            BindingResult result
    ) throws Exception {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    ObjectResponse.<String>builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .message(ValidationHelper.extractDetailedErrorMessages(result))
                            .data(null)
                            .build()
            );
        }
        String token = userServiceImpl.loginUser(userLoginDTO);
        return ResponseEntity.ok(
                ObjectResponse.<String>builder()
                        .status(HttpStatus.OK)
                        .message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_SUCCESSFULLY))
                        .data(token)
                        .build()
        );
    }

    @PostMapping("/details")
    public ResponseEntity<ObjectResponse<UserResponse>> getUserDetails(
            @RequestHeader("Authorization") String authorizationHeader
    ) throws Exception {
        String extractedToken = authorizationHeader.substring(7);
        User user = userServiceImpl.getUserDetailsFromToken(extractedToken);
        return ResponseEntity.ok(
                ObjectResponse.<UserResponse>builder()
                        .status(HttpStatus.OK)
                        .message("Successfully retrieved user information")
                        .data(UserResponse.fromUser(user))
                        .build()
        );
    }

    @PostMapping(value = "uploads/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ObjectResponse<Map>> postImage(
            @PathVariable("userId") Long userId,
            @RequestPart("files") List<MultipartFile> files
    ) throws Exception {
        fileServiceImpl.validateImageAvatar(files);
        MultipartFile file = files.get(0);
        String publicId = userServiceImpl.getPublicId(userId);
        if (!publicId.isEmpty()) {
            return ResponseEntity.ok(
                    ObjectResponse.<Map>builder()
                            .status(HttpStatus.OK)
                            .message("Avatar updated successfully")
                            .data(cloudinaryServiceImpl.updateImage(publicId, file))
                            .build()
            );
        }
        return ResponseEntity.ok(
                ObjectResponse.<Map>builder()
                        .status(HttpStatus.OK)
                        .message("Avatar uploaded successfully")
                        .data(cloudinaryServiceImpl.upload(file))
                        .build()
        );
    }

    @PutMapping("details/{userId}")
    public ResponseEntity<ObjectResponse<UserResponse>> updateUser(
            @PathVariable("userId") Long userId,
            @RequestBody UpdateUserDTO updateUserDTO,
            @RequestHeader("Authorization") String authorizationHeader
    ) throws Exception {
        String extractedToken = authorizationHeader.substring(7);
        User user = userServiceImpl.getUserDetailsFromToken(extractedToken);
        User updateUser = userServiceImpl.updateUser(user.getId(), updateUserDTO);
        return ResponseEntity.ok(
                ObjectResponse.<UserResponse>builder()
                        .status(HttpStatus.OK)
                        .message("User information updated successfully")
                        .data(UserResponse.fromUser(updateUser))
                        .build()
        );
    }

    @PutMapping("password/{userId}")
    public ResponseEntity<ObjectResponse<UserResponse>> updatePassword(
            @PathVariable("userId") Long userId,
            @RequestBody PasswordDTO passwordDTO,
            @RequestHeader("Authorization") String authorizationHeader
    ) throws Exception {
        String extractedToken = authorizationHeader.substring(7);
        User user = userServiceImpl.getUserDetailsFromToken(extractedToken);
        User updateUser = userServiceImpl.updatePassword(user.getId(), passwordDTO);
        return ResponseEntity.ok(
                ObjectResponse.<UserResponse>builder()
                        .status(HttpStatus.OK)
                        .message("Password changed successfully")
                        .data(UserResponse.fromUser(updateUser))
                        .build()
        );
    }

    @PutMapping("email/{userId}")
    public ResponseEntity<ObjectResponse<String>> updateEmail(
            @PathVariable("userId") Long userId,
            @RequestBody EmailDTO emailDTO,
            @RequestHeader("Authorization") String authorizationHeader
    ) throws Exception {
        String extractedToken = authorizationHeader.substring(7);
        User user = userServiceImpl.getUserDetailsFromToken(extractedToken);
        String token = userServiceImpl.updateEmail(user.getId(), emailDTO);
        return ResponseEntity.ok(
                ObjectResponse.<String>builder()
                        .status(HttpStatus.OK)
                        .message("Email changed successfully")
                        .data(token)
                        .build()
        );
    }

    @GetMapping("auth/social-login")
    public ResponseEntity<ObjectResponse<String>> socialAuth(
            @RequestParam("login_type") String loginType
    ) {
        loginType = loginType.trim().toLowerCase();
        String url = authServiceImpl.generateAuthUrl(loginType);
        return ResponseEntity.ok(
                ObjectResponse.<String>builder()
                        .status(HttpStatus.OK)
                        .message("Social login URL generated")
                        .data(url)
                        .build()
        );
    }

    @GetMapping("auth/social/callback")
    public ResponseEntity<ObjectResponse<String>> callback(
            @RequestParam("code") String code,
            @RequestParam(name = "login_type", required = false) String loginType,
            HttpServletRequest request
    ) throws Exception {
        Map<String, Object> userInfo = authServiceImpl.authenticateAndFetchProfile(code, loginType);

        String accountId = "";
        String name = "";
        String picture = "";
        String email = "";

        loginType = loginType != null ? loginType.trim().toLowerCase() : "";

        switch (loginType) {
            case "google":
                accountId = (String) Objects.requireNonNullElse(userInfo.get("sub"), "");
                name = (String) Objects.requireNonNullElse(userInfo.get("name"), "");
                picture = (String) Objects.requireNonNullElse(userInfo.get("picture"), "");
                email = (String) Objects.requireNonNullElse(userInfo.get("email"), "");
                break;

            case "facebook":
                accountId = (String) Objects.requireNonNullElse(userInfo.get("id"), "");
                name = (String) Objects.requireNonNullElse(userInfo.get("name"), "");
                email = (String) Objects.requireNonNullElse(userInfo.get("email"), "");
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
                break;

            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        ObjectResponse.<String>builder()
                                .status(HttpStatus.BAD_REQUEST)
                                .message("Invalid login type")
                                .data(null)
                                .build()
                );
        }

        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                .email(email)
                .fullName(name)
                .password("")
                .phoneNumber("")
                .avatar(picture)
                .facebookAccountId(loginType.equals("facebook") ? accountId : "")
                .googleAccountId(loginType.equals("google") ? accountId : "")
                .roleId(1L)
                .build();

        // Đăng nhập và trả token
        String token = userServiceImpl.loginUser(userLoginDTO);

        return ResponseEntity.ok(
                ObjectResponse.<String>builder()
                        .status(HttpStatus.OK)
                        .message("Social login successful")
                        .data(token)
                        .build()
        );
    }
}
