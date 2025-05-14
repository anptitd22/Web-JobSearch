package com.project.webIT.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.webIT.models.Company;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyResponse {
    private Long id;
    private String logo;

    @JsonProperty("public_id_images")
    private String publicIdImages;

    private String name;
    private String location;
    private String nation;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String email;

    private String description;

    private String contact;

    private Long size;

    private String industry;

    @JsonProperty("total_jobs")
    private Long totalJobs;

    @JsonProperty("role_id")
    private Long roleId;

    @JsonProperty("total_follow")
    private Long totalFollow;

    @JsonProperty("company_updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("company_is_active")
    private Boolean isActive;

    @JsonProperty("company_banner")
    private String companyBanner;

//    @JsonProperty("company_images")
//    private List<CompanyImage> companyImages = new ArrayList<>();

    public static CompanyResponse fromCompany(Company company){
        return CompanyResponse.builder()
                .id(company.getId())
                .logo(company.getLogo())
                .industry(company.getIndustry().getName())
                .name(company.getName())
                .location(company.getLocation())
                .nation(company.getNation())
                .phoneNumber(company.getPhoneNumber())
                .email(company.getEmail())
                .description(company.getDescription())
                .contact(company.getContact())
                .size(company.getSize())
                .publicIdImages(company.getPublicIdImages())
                .totalJobs((long) company.getTotal_jobs())
                .roleId(company.getRole().getId())
                .totalFollow(company.getTotal_follow())
                .updatedAt(company.getUpdatedAt())
                .isActive(company.isActive())
                .companyBanner(company.getBanner())
//                .companyImages(company.getCompanyImages())
                .build();
    }
}
