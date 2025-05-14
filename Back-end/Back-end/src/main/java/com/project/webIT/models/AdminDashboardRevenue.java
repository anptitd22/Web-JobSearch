package com.project.webIT.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admin_dashboard_revenue")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDashboardRevenue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Column(name = "month", nullable = false)
    private String month;

    @Column(name = "total_revenue", nullable = false)
    private Double totalRevenue;
}
