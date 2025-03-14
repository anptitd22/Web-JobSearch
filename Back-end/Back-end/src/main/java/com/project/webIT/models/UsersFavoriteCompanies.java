package com.project.webIT.models;

import com.project.webIT.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users_favorite_companies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersFavoriteCompanies extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "is_active", nullable = false)
    private boolean isActive ;
}
