package com.project.webIT.response.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.webIT.models.*;
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
    private Job job;

    @JsonProperty("is_active")
    private boolean isActive;

    public static UsersFavoriteJobsResponse fromUserFavoriteJobs(UsersFavoriteJobs usersFavoriteJobs){
        return UsersFavoriteJobsResponse.builder()
                .id(usersFavoriteJobs.getId())
                .job(usersFavoriteJobs.getJob())
                .isActive(usersFavoriteJobs.isActive())
                .build();
    }
}
