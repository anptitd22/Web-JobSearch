package com.project.webIT.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "applied_job")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppliedJob extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id") //khong can thiet vi id giong mysql
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "nationality", nullable = false, length = 50)
    private String nationality;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "job_id")
    @JsonBackReference
    private Job job;

    @Column(name = "address", nullable = false, length = 200)
    private String address;

    @Column(name = "job_title", nullable = false, length = 200)
    private String jobTitle;

    @Column(name = "current_job_level", nullable = false, length = 200)
    private String currentJobLevel;

    @Column(name = "note", length = 100)
    private String note;

    @Column(name = "apply_date")
    private LocalDateTime applyDate;

    @Column(name = "expected_date")
    private LocalDateTime expectedDate;

    @Column(name = "feedback_date")
    private LocalDateTime feedBackDate;

    @Column(name = "status")
    private String status;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "highest_education", nullable = false, length = 200)
    private String highestEducation;

    @Column(name = "current_industry", nullable = false, length = 200)
    private String currentIndustry;

    @Column(name = "current_job_function")
    private String currentJobFunction;

    @Column(name = "years_of_experience")
    private Long yearsOfExperience;

    public static final String PENDING = "Pending";
    public static final String PROCESSING = "Processing";
    public static final String FINISHED = "Finished";
}
