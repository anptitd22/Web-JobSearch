package com.project.webIT.controllers;

import com.project.webIT.dtos.users.UpdateUserDTO;
import com.project.webIT.dtos.users.UserDTO;
import com.project.webIT.dtos.users.UserLoginDTO;
import com.project.webIT.models.User;
import com.project.webIT.response.auth.LoginResponse;
import com.project.webIT.response.auth.RegisterResponse;
import com.project.webIT.response.users.UserResponse;
import com.project.webIT.services.UserService;
import com.project.webIT.components.LocalizationUtils;
import com.project.webIT.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final LocalizationUtils localizationUtils;

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

    @PutMapping("details/{userId}")
    @Transactional
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
}
