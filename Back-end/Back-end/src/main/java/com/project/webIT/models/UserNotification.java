package com.project.webIT.models;

import com.project.webIT.constant.UserNotificationStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_notifications")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserNotification extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserNotificationStatus userNotificationStatus;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @Column(name = "content")
    private String content;

    @Column(name = "is_active")
    private Boolean isActive;
}
