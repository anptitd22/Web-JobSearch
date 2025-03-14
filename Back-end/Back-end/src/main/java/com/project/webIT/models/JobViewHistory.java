package com.project.webIT.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.webIT.utils.ViewEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
