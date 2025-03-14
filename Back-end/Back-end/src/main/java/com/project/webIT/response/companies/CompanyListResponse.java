package com.project.webIT.response.companies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CompanyListResponse {
    private List<CompanyResponse> companies;
    private int totalPages;
    private long totalCompanies;
}
