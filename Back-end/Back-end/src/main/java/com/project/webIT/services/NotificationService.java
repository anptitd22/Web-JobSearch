package com.project.webIT.services;

import com.project.webIT.notification.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    // Gửi thông báo đến một người dùng cụ thể
    public void sendNotificationToUser(String userId, Notification notification) {
        log.info("Sending notification to user {} with payload {}", userId, notification);
        simpMessagingTemplate.convertAndSendToUser(
                userId,
                "/topic/notifications",
                notification);
    }

    // Gửi thông báo đến nhiều người dùng
    public void sendNotificationToUsers(List<String> userIds, Notification notification) {
        log.info("Sending notification to {} users with payload {}", userIds.size(), notification);
        for (String userId : userIds) {
            sendNotificationToUser(userId, notification);
        }
    }

    //Gửi thông báo đến toàn hệ thống
    public void broadcastNotification(Notification notification) {
        log.info("Sending WS notification to people with payload {}", notification);
        simpMessagingTemplate.convertAndSend(
                "/topic/notifications",
                notification);
    }
}
