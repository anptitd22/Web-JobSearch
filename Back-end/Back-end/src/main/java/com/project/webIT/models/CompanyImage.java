package com.project.webIT.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "company_images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyImage {
    public static final int MAXIMUM_IMAGES_PER_COMPANY = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "image_url", nullable = false, length = 300)
    private String imageUrl;
}
