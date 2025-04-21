package com.project.webIT.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.webIT.models.AppliedJobCV;
import com.project.webIT.models.UserCV;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppliedJobCVResponse {

    private Long id;

    @JsonProperty("cv_url")
    private String cvUrl;

    @JsonProperty("public_id_cv")
    private String publicIdCV;

    @JsonProperty("cv_name")
    private String cvName;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    public static AppliedJobCVResponse fromAppliedJobCV(AppliedJobCV appliedJobCV) {
        return AppliedJobCVResponse.builder()
                .id(appliedJobCV.getId())
                .cvUrl(appliedJobCV.getCvUrl())
                .publicIdCV(appliedJobCV.getPublicIdCV())
                .cvName(appliedJobCV.getName())
                .updatedAt(appliedJobCV.getUpdatedAt())
                .build();
    }
}
