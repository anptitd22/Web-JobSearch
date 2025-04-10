package com.project.webIT.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.webIT.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id") //khong can thiet vi id giong mysql
    private Long id;

    @Column(name="avatar")
    private String avatar;

    @Column(name="public_id_images")
    private String publicIdImages;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "password", length = 200)
    private String password;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "facebook_account_id")
    private String facebookAccountId;

    @Column(name = "google_account_id")
    private String googleAccountId;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "highest_education", length = 200)
    private String highestEducation;

    @Column(name = "current_industry", length = 200)
    private String currentIndustry;

    @Column(name = "current_job_function")
    private String currentJobFunction;

    @Column(name = "years_of_experience")
    private Long yearsOfExperience;

    @Column(name = "current_job_level", length = 200)
    private String currentJobLevel;

    @Column(name = "job_title", length = 200)
    private String jobTitle;

    @Column(name = "note", length = 200)
    private String note;

    @Column(name = "nationality", length = 50)
    private String nationality;

    @Column(name = "gender", length = 50)
    private String gender;

    @Column(name = "marital_status", length = 50)
    private String maritalStatus;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("updatedAt DESC")
    @JsonManagedReference
    private List<AppliedJob> appliedJobs = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @Where(clause = "is_active = true")
    private List<UserCV> userCVS = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("createdAt DESC")
    @JsonManagedReference
    private List<UserPayment> userPayments = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
//        authorityList.add(new SimpleGrantedAuthority("ROLE_"+getRole().getName().toUpperCase()));
//        authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.getName().toUpperCase()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
