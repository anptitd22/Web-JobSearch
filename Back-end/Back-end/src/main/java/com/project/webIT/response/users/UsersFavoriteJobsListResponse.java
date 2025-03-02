package com.project.webIT.response.users;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UsersFavoriteJobsListResponse {
    private List<UsersFavoriteJobsResponse> usersFavoriteJobsListResponse;
}
