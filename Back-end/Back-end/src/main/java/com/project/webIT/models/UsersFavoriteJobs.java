package com.project.webIT.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users_favorite_jobs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersFavoriteJobs extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @Column(name = "is_active", nullable = false)
    private boolean isActive ;
}
