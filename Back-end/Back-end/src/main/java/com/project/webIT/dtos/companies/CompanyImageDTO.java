package com.project.webIT.dtos.companies;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CompanyImageDTO {
    @JsonProperty("company_id")
    private Long companyId;

    @Size(min = 5, max = 300, message = "Image's name")
    @JsonProperty("image_url")
    private String imageUrl;
}
