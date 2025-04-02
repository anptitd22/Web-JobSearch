package com.project.webIT.controllers;

import com.project.webIT.models.UsersFavoriteJobs;
import com.project.webIT.response.users.UsersFavoriteJobsResponse;
import com.project.webIT.services.UsersFavoriteJobsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/my-career-center")
@RequiredArgsConstructor
public class UserFavoriteJobsController {
    private final UsersFavoriteJobsService favoriteJobService;

    @PostMapping("{userId}/{jobId}")
    public ResponseEntity<?> saveFavorite(
            @PathVariable("userId") Long userId,
            @PathVariable("jobId") Long jobId
    ) {
        try{
            UsersFavoriteJobs usersFavoriteJobs = favoriteJobService.saveFavoriteJob(userId, jobId);
            return ResponseEntity.ok(UsersFavoriteJobsResponse.fromUserFavoriteJobs(usersFavoriteJobs));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("my-jobs/{userId}")
    public ResponseEntity<?> getFavorites(@PathVariable("userId") Long userId){
        try{
            List<UsersFavoriteJobs> usersFavoriteJobsList = favoriteJobService.getUserFavorites(userId);
            List<UsersFavoriteJobsResponse> responseList = usersFavoriteJobsList.stream()
                    .map(UsersFavoriteJobsResponse::fromUserFavoriteJobs)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responseList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("my-jobs/default/{userId}")
    public ResponseEntity<?> getFavoritesDefault(@PathVariable("userId") Long userId){
        try{
            List<UsersFavoriteJobs> usersFavoriteJobsList = favoriteJobService.getUserFavoritesDefault(userId);
            List<UsersFavoriteJobsResponse> responseList = usersFavoriteJobsList.stream()
                    .map(UsersFavoriteJobsResponse::fromUserFavoriteJobs)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responseList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
