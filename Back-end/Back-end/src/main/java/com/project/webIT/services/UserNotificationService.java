package com.project.webIT.services;

import com.project.webIT.constant.UserNotificationStatus;
import com.project.webIT.exceptions.DataNotFoundException;
import com.project.webIT.models.UserNotification;
import com.project.webIT.repositories.UserNotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserNotificationService {
    private final UserNotificationRepository userNotificationRepository;

    public List<UserNotification> findByUser(Long userId) {
        return userNotificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Transactional
    public void updateNotification(Long id) throws Exception {
        UserNotification userNotification = userNotificationRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("notification not found"));
        userNotification.setUserNotificationStatus(UserNotificationStatus.Read);
        userNotificationRepository.save(userNotification);
    }
}
