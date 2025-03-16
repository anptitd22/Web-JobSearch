package com.project.webIT.controllers;

import com.project.webIT.models.Industry;
import com.project.webIT.models.JobFunction;
import com.project.webIT.services.IndustryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/industries")
@RequiredArgsConstructor
public class IndustryController {
    private final IndustryService industryService;

    @GetMapping("")
    public ResponseEntity<?> getAllIndustries(){
        List<Industry> industries = industryService.getAllIndustries();
        return ResponseEntity.ok().body(industries);
    }
}
