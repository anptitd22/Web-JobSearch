package com.project.webIT.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.webIT.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company{
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

    @Column(name = "logo", length = 500)
    private String logo;

    @Column(name = "contact")
    private String contact;

    @Column(name = "size")
    private Long size;

    @Column(name = "is_active")
    private boolean isActive;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Job> jobs = new ArrayList<>();
}
