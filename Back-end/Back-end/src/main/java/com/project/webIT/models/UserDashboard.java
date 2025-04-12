package com.project.webIT.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_dashboards")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDashboard extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "month", nullable = false)
    private String month;

    @Column(name = "applied_jobs", nullable = false)
    private Long appliedJobs;

    @Column(name = "job_views", nullable = false)
    private Long jobViews;

    @Column(name = "job_searches", nullable = false)
    private Long jobSearches;
}
