package com.project.webIT.notification;

import com.project.webIT.constant.NotificationStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {
    private NotificationStatus status;
    private String message;
    private String jobTitle;
    private Long jobId;
}
