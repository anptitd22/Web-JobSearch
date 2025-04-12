package com.project.webIT.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "job_view_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobViewHistory extends ViewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id") //khong can thiet vi id giong mysql
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @Column(name = "view_count")
    private Long viewCount;

    @Column(name = "is_active")
    private boolean isActive;
}
