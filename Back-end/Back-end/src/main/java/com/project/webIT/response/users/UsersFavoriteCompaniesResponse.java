package com.project.webIT.response.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.webIT.models.UsersFavoriteCompanies;
import com.project.webIT.models.UsersFavoriteJobs;
import com.project.webIT.response.companies.CompanyResponse;
import com.project.webIT.response.jobs.JobResponse;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UsersFavoriteCompaniesResponse {
    private Long id;

    @JsonProperty("company")
    private CompanyResponse companyResponse;

    @JsonProperty("is_active")
    private boolean isActive;

    public static UsersFavoriteCompaniesResponse fromUserFavoriteCompanies(UsersFavoriteCompanies usersFavoriteCompanies){
        return UsersFavoriteCompaniesResponse.builder()
                .id(usersFavoriteCompanies.getId())
                .companyResponse(CompanyResponse.fromCompany(usersFavoriteCompanies.getCompany()))
                .isActive(usersFavoriteCompanies.isActive())
                .build();
    }
}
