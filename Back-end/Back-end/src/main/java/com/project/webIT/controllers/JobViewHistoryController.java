package com.project.webIT.controllers;

import com.project.webIT.models.JobViewHistory;
import com.project.webIT.dtos.response.ObjectResponse;
import com.project.webIT.dtos.response.JobViewHistoryResponse;
import com.project.webIT.models.User;
import com.project.webIT.services.JobViewHistoryServiceImpl;
import com.project.webIT.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/history")
@RequiredArgsConstructor
public class JobViewHistoryController {
    private final JobViewHistoryServiceImpl jobViewHistoryServiceImpl;
    private final UserServiceImpl userService;

    @GetMapping("")
    public ResponseEntity<ObjectResponse<List<JobViewHistoryResponse>>> getJobViewHistories(
            @RequestHeader("Authorization") String authorizationHeader
    ) throws Exception {
        String extractedToken = authorizationHeader.substring(7);
        User user = userService.getUserDetailsFromToken(extractedToken);
        List<JobViewHistory> jobViewHistories = jobViewHistoryServiceImpl.jobViewHistories(user.getId());
        List<JobViewHistoryResponse> responses = jobViewHistories.stream()
                .map(JobViewHistoryResponse::fromJobViewHistoryResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                ObjectResponse.<List<JobViewHistoryResponse>>builder()
                        .status(HttpStatus.OK)
                        .message("Retrieved viewed jobs successfully")
                        .data(responses)
                        .build()
        );
    }

    @PostMapping("user/{jobId}")
    public ResponseEntity<ObjectResponse<JobViewHistoryResponse>> saveJobViewHistory(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("jobId") Long jobId
    ) throws Exception {
        String extractedToken = authorizationHeader.substring(7);
        User user = userService.getUserDetailsFromToken(extractedToken);
        JobViewHistory jobViewHistory = jobViewHistoryServiceImpl.saveJobViewHistory(user.getId(), jobId);

        return ResponseEntity.ok(
                ObjectResponse.<JobViewHistoryResponse>builder()
                        .status(HttpStatus.OK)
                        .message("Job view saved successfully")
                        .data(JobViewHistoryResponse.fromJobViewHistoryResponse(jobViewHistory))
                        .build()
        );
    }
}
