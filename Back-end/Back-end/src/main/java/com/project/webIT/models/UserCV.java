package com.project.webIT.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_cvs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCV extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="cv_url")
    private String cvUrl;

    @Column(name="public_id_cv")
    private String publicIdCv;

    @Column(name="is_active")
    private Boolean isActive;
}
