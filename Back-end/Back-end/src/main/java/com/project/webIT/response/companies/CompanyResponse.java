package com.project.webIT.response.companies;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.webIT.models.Company;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyResponse {

    private String name;
    private String location;
    private String nation;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String email;

    private String description;

    private String thumbnail;

    private String contact;

    private Long size;

//    @JsonProperty("company_images")
//    private List<CompanyImage> companyImages = new ArrayList<>();

    public static CompanyResponse fromCompany(Company company){
        return CompanyResponse.builder()
                .name(company.getName())
                .location(company.getLocation())
                .nation(company.getNation())
                .phoneNumber(company.getPhoneNumber())
                .email(company.getEmail())
                .description(company.getDescription())
                .thumbnail(company.getThumbnail())
                .contact(company.getContact())
                .size(company.getSize())
//                .companyImages(company.getCompanyImages())
                .build();
    }
}
