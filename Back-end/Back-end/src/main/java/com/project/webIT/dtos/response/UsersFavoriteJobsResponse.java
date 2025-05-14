package com.project.webIT.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.webIT.models.UserFavoriteJob;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UsersFavoriteJobsResponse {
    private Long id;

    @JsonProperty("job")
    private JobResponse jobResponse;

    @JsonProperty("is_active")
    private boolean isActive;

    public static UsersFavoriteJobsResponse fromUserFavoriteJobs(UserFavoriteJob userFavoriteJob){
        return UsersFavoriteJobsResponse.builder()
                .id(userFavoriteJob.getId())
                .jobResponse(JobResponse.fromJob(userFavoriteJob.getJob()))
                .isActive(userFavoriteJob.isActive())
                .build();
    }
}
