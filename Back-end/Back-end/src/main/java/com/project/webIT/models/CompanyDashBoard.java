package com.project.webIT.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "company_dashboards")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyDashBoard extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "month", nullable = false)
    private String month;

    @Column(name = "applied_jobs", nullable = false)
    private Long appliedJobs;

    @Column(name = "total_jobs", nullable = false)
    private Long totalJobs;

    @Column(name = "applied_job_accept", nullable = false)
    private Long appliedJobAccept;
}
