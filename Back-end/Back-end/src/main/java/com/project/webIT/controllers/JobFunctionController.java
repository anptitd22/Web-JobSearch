package com.project.webIT.controllers;

import com.project.webIT.dtos.jobs.JobFunctionDTO;
import com.project.webIT.models.JobFunction;
import com.project.webIT.services.JobFunctionService;
import jakarta.validation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/functions")
//@Validated
//Dependency Injection
@RequiredArgsConstructor
public class JobFunctionController {

    private final JobFunctionService jobFunctionService;
    @PostMapping("")
    public ResponseEntity<?> createJobFunction(
            @Valid @RequestBody JobFunctionDTO jobFunctionDTO,
            BindingResult result
    ){
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            jobFunctionService.createJobFunction(jobFunctionDTO);
            return ResponseEntity.ok().body("create JobFunction successfully " + jobFunctionDTO);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getJobFunctions(){
        List<JobFunction> jobFunctions = jobFunctionService.getAllJobFunctions();
        return ResponseEntity.ok().body(jobFunctions);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateJobFunction(
            @Valid @PathVariable Long id,
            @Valid @RequestBody JobFunctionDTO jobFunctionDTO
    ){
        JobFunction newJobFunction = jobFunctionService.updateJobFunction(id,jobFunctionDTO);
        return ResponseEntity.ok().body(newJobFunction);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deleteJobFunction(@Valid @PathVariable Long id){
        jobFunctionService.deleteJobFunction(id);
        return ResponseEntity.ok().body("delete JobFunction successfully with id = "+id);
    }
}
