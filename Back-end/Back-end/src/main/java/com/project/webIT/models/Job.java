package com.project.webIT.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.webIT.settime.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "jobs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Job extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id") //khong can thiet vi id giong mysql
    private Long id;

    @Column(name = "name", nullable = false, length = 350)
    private String name;

    private String salary;

    @Column(name = "salary_numeric")
    private Float salaryNumeric;

    @Column(name = "type_of_work")
    private String typeOfWork;

    @Column(name = "job_locations", nullable = false, length = 400)
    private String jobLocations;

    @Column(name = "thumbnail", length = 300)
    private String thumbnail;

    @Column(name = "description")
    private String description;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    @Column(name = "active")
    private boolean active;

    @Column(name = "view")
    private Long view;

    @ManyToOne
    @JoinColumn(name = "job_function_id")
    private JobFunction jobFunction;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<JobImage> jobImages = new ArrayList<>();
}
