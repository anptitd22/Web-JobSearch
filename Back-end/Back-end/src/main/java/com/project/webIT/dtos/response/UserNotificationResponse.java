package com.project.webIT.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.webIT.constant.UserNotificationStatus;
import com.project.webIT.models.User;
import com.project.webIT.models.UserNotification;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserNotificationResponse {

    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("status")
    private UserNotificationStatus userNotificationStatus;

    @JsonProperty("is_active")
    private Boolean isActive;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("job")
    private JobResponse jobResponse;

    @JsonProperty("content")
    private String content;

    public static UserNotificationResponse fromUserNotification(UserNotification userNotification){
        return UserNotificationResponse.builder()
                .id(userNotification.getId())
                .userId(userNotification.getUser().getId())
                .isActive(userNotification.getIsActive())
                .userNotificationStatus(userNotification.getUserNotificationStatus())
                .createdAt(userNotification.getCreatedAt())
                .jobResponse(JobResponse.fromJob(userNotification.getJob()))
                .content(userNotification.getContent())
                .build();
    }
}
