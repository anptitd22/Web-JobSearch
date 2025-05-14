package com.project.webIT.controllers;

import com.project.webIT.dtos.request.JobRecommendDTO;
import com.project.webIT.dtos.request.UserInputDTO;
import com.project.webIT.dtos.request.UserRecommendDTO;
import com.project.webIT.dtos.response.JobResponse;
import com.project.webIT.dtos.response.ObjectResponse;
import com.project.webIT.helper.ValidationHelper;
import com.project.webIT.models.Job;
import com.project.webIT.repositories.JobRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/jobs")
@RequiredArgsConstructor
@Slf4j
public class JobRecommendationController {
    private final RestTemplate restTemplate;
    private final JobRepository jobRepository;

    @PostMapping("recommend")
    public ResponseEntity<ObjectResponse<?>> recommendJobs(
//            @RequestBody UserInputDTO user
            @Valid @RequestBody JobRecommendDTO jobRecommendDTO,
            BindingResult result
    ) {
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(
                    ObjectResponse.<Void>builder()
                            .message(ValidationHelper.extractDetailedErrorMessages(result))
                            .status(HttpStatus.BAD_REQUEST)
                            .build()
            );
        }
        String url = "http://localhost:8000/recommend"; // FastAPI endpoint

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<JobRecommendDTO> request = new HttpEntity<>(jobRecommendDTO, headers);

        ResponseEntity<List<Integer>> response =
                restTemplate.exchange(url, HttpMethod.POST, request,
                        new ParameterizedTypeReference<List<Integer>>() {});
        List<Integer> recommendedIds = response.getBody();

        // Truy vấn database để lấy thông tin chi tiết
        List<Job> jobs = jobRepository.findByIdIn(recommendedIds);
        List<JobResponse> jobResponses = jobs.stream()
                        .map(JobResponse::fromJob)
                                .collect(Collectors.toList());

        return ResponseEntity.ok().body(
                ObjectResponse.<List<JobResponse>>builder()
                        .status(HttpStatus.OK)
                        .message("success")
                        .data(jobResponses)
                        .build()
        );
    }

    @PostMapping("recommend/user")
    public ResponseEntity<ObjectResponse<?>> recommendJobs(
            @Valid @RequestBody UserRecommendDTO userRecommendDTO,
            BindingResult result
    ) {
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(
                    ObjectResponse.<Void>builder()
                            .message(ValidationHelper.extractDetailedErrorMessages(result))
                            .status(HttpStatus.BAD_REQUEST)
                            .build()
            );
        }
        String url = "http://localhost:8000/recommend/user"; // FastAPI endpoint

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserRecommendDTO> request = new HttpEntity<>(userRecommendDTO, headers);

        ResponseEntity<List<Integer>> response =
                restTemplate.exchange(url, HttpMethod.POST, request,
                        new ParameterizedTypeReference<List<Integer>>() {});
        List<Integer> recommendedIds = response.getBody();

        // Truy vấn database để lấy thông tin chi tiết
        List<Job> jobs = jobRepository.findByIdIn(recommendedIds);
        List<JobResponse> jobResponses = jobs.stream()
                .map(JobResponse::fromJob)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(
                ObjectResponse.<List<JobResponse>>builder()
                        .status(HttpStatus.OK)
                        .message("success")
                        .data(jobResponses)
                        .build()
        );
    }
}
