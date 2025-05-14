package com.project.webIT.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.webIT.models.UserFavoriteCompany;
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

    public static UsersFavoriteCompaniesResponse fromUserFavoriteCompanies(UserFavoriteCompany userFavoriteCompany){
        return UsersFavoriteCompaniesResponse.builder()
                .id(userFavoriteCompany.getId())
                .companyResponse(CompanyResponse.fromCompany(userFavoriteCompany.getCompany()))
                .isActive(userFavoriteCompany.isActive())
                .build();
    }
}
