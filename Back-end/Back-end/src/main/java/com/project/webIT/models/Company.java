package com.project.webIT.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "company")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id") //khong can thiet vi id giong mysql
    private Long id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "location", nullable = false, length = 200)
    private String location;

    @Column(name = "nation", nullable = false, length = 100)
    private String nation;

    @Column(name = "phone_number", nullable = false, length = 10)
    private String phoneNumber;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "description")
    private String description;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "contact")
    private String contact;

    @Column(name = "size")
    private Long size;

    @Column(name = "active")
    private boolean active;

//    @OneToMany
//    @JoinColumn(name = "id")
//    private List<CompanyImage> companyImages = new ArrayList<>();
}
