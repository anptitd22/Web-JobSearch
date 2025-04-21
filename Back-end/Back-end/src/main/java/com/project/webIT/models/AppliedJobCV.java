package com.project.webIT.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "applied_job_cvs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppliedJobCV extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "applied_job_id")
    @JsonBackReference
    private AppliedJob appliedJob;

    @Column(name = "cv_url")
    private String cvUrl;

    @Column(name = "public_id_cv")
    private String publicIdCV;

    @Column(name="is_active", nullable = false)
    private Boolean isActive;
}
