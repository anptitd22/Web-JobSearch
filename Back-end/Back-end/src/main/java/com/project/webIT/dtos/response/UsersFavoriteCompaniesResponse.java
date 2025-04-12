package com.project.webIT.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.webIT.models.UsersFavoriteCompanies;
import lombok.*;

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
