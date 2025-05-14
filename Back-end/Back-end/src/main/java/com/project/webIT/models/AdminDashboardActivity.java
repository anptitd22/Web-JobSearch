package com.project.webIT.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admin_dashboard_activity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDashboardActivity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Column(name = "month", nullable = false)
    private String month;

    @Column(name = "total_jobs", nullable = false)
    private Long totalJobs;

    @Column(name = "total_company", nullable = false)
    private Long totalCompany;

    @Column(name = "total_user", nullable = false)
    private Long totalUser;
}
