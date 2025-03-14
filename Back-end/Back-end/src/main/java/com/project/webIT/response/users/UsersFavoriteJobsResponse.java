package com.project.webIT.response.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.webIT.models.UsersFavoriteJobs;
import com.project.webIT.response.jobs.JobResponse;
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

    public static UsersFavoriteJobsResponse fromUserFavoriteJobs(UsersFavoriteJobs usersFavoriteJobs){
        return UsersFavoriteJobsResponse.builder()
                .id(usersFavoriteJobs.getId())
                .jobResponse(JobResponse.fromJob(usersFavoriteJobs.getJob()))
                .isActive(usersFavoriteJobs.isActive())
                .build();
    }
}
