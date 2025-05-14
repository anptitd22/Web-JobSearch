package com.project.webIT.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.webIT.constant.AppliedJobStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

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
    @Enumerated(EnumType.STRING)
    private AppliedJobStatus appliedJobStatus;

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

    @OneToMany (mappedBy = "appliedJob", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Where(clause = "is_active = true")
    @JsonManagedReference
    private List<AppliedJobCV> appliedJobCVList = new ArrayList<>();

    public static final Map<String, String> STATUS_MAP = Map.of(
            "Pending", "Đang duyệt",
            "Accept", "Chấp nhận",
            "Refuse", "Từ chối",
            "Interview", "Đã phỏng vấn"
    );
}
