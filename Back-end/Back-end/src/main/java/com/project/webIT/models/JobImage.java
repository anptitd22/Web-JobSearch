package com.project.webIT.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "job_images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobImage {
    public static final int MAXIMUM_IMAGES_PER_JOB = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id") //khong can thiet vi id giong mysql
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_id")
    @JsonBackReference
    private Job job;

    @Column(name = "image_url", nullable = false, length = 300)
    private String imageUrl;
}
