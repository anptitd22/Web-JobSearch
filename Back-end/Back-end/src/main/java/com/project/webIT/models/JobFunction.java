package com.project.webIT.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "job_functions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobFunction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id") //khong can thiet vi id giong mysql
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
}
