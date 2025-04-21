package com.project.webIT.controllers;

import com.project.webIT.dtos.request.UserInputDTO;
import com.project.webIT.dtos.response.ObjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/jobs")
@RequiredArgsConstructor
public class JobRecommendationController {
    private final RestTemplate restTemplate;

    @PostMapping("recommend")
    public ResponseEntity<ObjectResponse<List<Object>>> recommendJobs(@RequestBody UserInputDTO user) {
        String url = "http://localhost:8000/recommend"; // FastAPI endpoint

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserInputDTO> request = new HttpEntity<>(user, headers);

        ResponseEntity<List<Object>> response =
                restTemplate.exchange(url, HttpMethod.POST, request,
                        new ParameterizedTypeReference<List<Object>>() {});

        return ResponseEntity.ok().body(
                ObjectResponse.<List<Object>>builder()
                        .status(HttpStatus.OK)
                        .message("success")
                        .data(response.getBody())
                        .build()
        );
    }
}
