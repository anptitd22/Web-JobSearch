package com.project.webIT.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "companies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="account", nullable = false,length = 255)
    private String account;

    @Column(name="password", nullable = false,length = 255)
    private String password;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @ManyToOne
    @JoinColumn(name = "industry_id", nullable = false)
    private Industry industry;

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

    @Column(name = "public_id_images")
    private String publicIdImages;

    @Column(name = "contact")
    private String contact;

    @Column(name = "size")
    private Long size;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "banner")
    private String banner;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

//    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonManagedReference
//    private List<Job> jobs = new ArrayList<>();

    @Formula("(SELECT COUNT(*) FROM jobs j WHERE j.company_id = id AND j.is_active = 1)")
    private Long total_jobs;

    @Formula("(SELECT COUNT(*) FROM users_favorite_companies u WHERE u.company_id = id AND u.is_active = 1)")
    private Long total_follow;

//    @Formula("(SELECT COUNT(*) FROM applied_job a JOIN jobs j ON a.job_id = j.id JOIN companies c ON j.company_id = c.id")
//    private Long total_applied_jobs;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.getName().toUpperCase()));
    }

    @Override
    public String getUsername() {
        return account;
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
        return this.isActive;
    }
}
