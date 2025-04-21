package com.project.webIT.controllers;

import com.project.webIT.dtos.response.ObjectResponse;
import com.project.webIT.dtos.response.UserNotificationResponse;
import com.project.webIT.models.User;
import com.project.webIT.models.UserNotification;
import com.project.webIT.services.UserNotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/notification")
@RequiredArgsConstructor
@Slf4j
public class UserNotificationController {
    private final UserNotificationService userNotificationService;

    @GetMapping("get")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ObjectResponse<List<UserNotificationResponse>>> getAllNotification(
            @AuthenticationPrincipal User user
    ){
        List<UserNotification> userNotifications = userNotificationService.findByUser(user.getId());
        List<UserNotificationResponse> userNotificationResponses = userNotifications.stream()
                .map(UserNotificationResponse::fromUserNotification)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(
                ObjectResponse.<List<UserNotificationResponse>>builder()
                        .message("get all notification successfully")
                        .status(HttpStatus.OK)
                        .data(userNotificationResponses)
                        .build()
        );
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ObjectResponse<?>> putNotification(
            @Valid @PathVariable("id") Long id,
            @AuthenticationPrincipal User user
    )throws Exception{
        userNotificationService.updateNotification(id);
        return ResponseEntity.ok().body(
                ObjectResponse.builder()
                        .status(HttpStatus.OK)
                        .message("notification is read")
                        .build()
        );
    }
}
