package com.project.webIT.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.webIT.models.UserCV;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserCVResponse {

    private Long id;

    @JsonProperty("cv_url")
    private String cvUrl;

    @JsonProperty("public_id_cv")
    private String publicIdCv;

    @JsonProperty("cv_name")
    private String cvName;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    public static UserCVResponse fromUserCV(UserCV userCV) {
        return UserCVResponse.builder()
                .id(userCV.getId())
                .cvUrl(userCV.getCvUrl())
                .publicIdCv(userCV.getPublicIdCv())
                .cvName(userCV.getName())
                .updatedAt(userCV.getUpdatedAt())
                .build();
    }
}
