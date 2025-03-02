package com.project.webIT.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "company_feedback")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id") //khong can thiet vi id giong mysql
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "applied_job_id")
    private AppliedJob appliedJob;

    @Column(name = "status")
    private String status;

    @Column(name = "Notification")
    private String Notification;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
