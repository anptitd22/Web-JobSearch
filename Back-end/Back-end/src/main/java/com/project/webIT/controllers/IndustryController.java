package com.project.webIT.controllers;

import com.project.webIT.models.Industry;
import com.project.webIT.dtos.response.ObjectResponse;
import com.project.webIT.services.IndustryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/industries")
@RequiredArgsConstructor
public class IndustryController {
    private final IndustryServiceImpl industryServiceImpl;

    @GetMapping("")
    public ResponseEntity<ObjectResponse<List<Industry>>> getAllIndustries() throws Exception {
        List<Industry> industries = industryServiceImpl.getAllIndustries();
        return ResponseEntity.ok(
                ObjectResponse.<List<Industry>>builder()
                        .status(HttpStatus.OK)
                        .message("Industry list retrieved successfully")
                        .data(industries)
                        .build()
        );
    }
}
