package com.project.webIT.controllers;

import com.project.webIT.models.JobViewHistory;
import com.project.webIT.models.UsersFavoriteJobs;
import com.project.webIT.repositories.JobViewHistoryRepository;
import com.project.webIT.response.jobs.JobViewHistoryResponse;
import com.project.webIT.response.users.UsersFavoriteJobsResponse;
import com.project.webIT.services.JobViewHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/history")
@RequiredArgsConstructor
public class JobViewHistoryController {
    private final JobViewHistoryService jobViewHistoryService;

    @GetMapping("{userId}")
    public ResponseEntity<?> saveFavorite(
            @PathVariable("userId") Long userId
    ) {
        try{
            List<JobViewHistory> jobViewHistories = jobViewHistoryService.jobViewHistories(userId);
            return ResponseEntity.ok(jobViewHistories.stream()
                    .map(JobViewHistoryResponse::fromJobViewHistoryResponse)
                    .collect(Collectors.toList()));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("{userId}/{jobId}")
    public ResponseEntity<?> saveFavorite(
            @PathVariable("userId") Long userId,
            @PathVariable("jobId") Long jobId
    ) {
        try{
            JobViewHistory jobViewHistory = jobViewHistoryService.saveJobViewHistory(userId, jobId);
            return ResponseEntity.ok(JobViewHistoryResponse.fromJobViewHistoryResponse(jobViewHistory));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
