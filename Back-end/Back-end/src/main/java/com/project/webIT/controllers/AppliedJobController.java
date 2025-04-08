package com.project.webIT.controllers;

import com.project.webIT.dtos.appliedJob.AppliedJobDTO;
import com.project.webIT.exceptions.DataNotFoundException;
import com.project.webIT.models.AppliedJob;
import com.project.webIT.response.appliedJob.AppliedJobResponse;
import com.project.webIT.services.AppliedJobService;
import com.project.webIT.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/applied")
@RequiredArgsConstructor
public class AppliedJobController {
    private final AppliedJobService appliedJobService;
    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<?> createAppliedJob(
            @Valid @RequestBody AppliedJobDTO appliedJobDTO,
            BindingResult result
    ){
        try {
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            AppliedJob appliedJob = appliedJobService.createAppliedJob(appliedJobDTO);
            return ResponseEntity.ok().body(AppliedJobResponse.fromAppliedJob(appliedJob));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("user/{userId}") //danh sach apply
    public ResponseEntity<?> getAppliedJobsFromUser(
            @Valid @PathVariable("userId") Long userId
    ){
        try {
            List<AppliedJob> appliedJobs = appliedJobService.getAppliedJobFromUser(userId);
            return ResponseEntity.ok().body(appliedJobs.stream()
                    .map(AppliedJobResponse::fromAppliedJob)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("job/{job_id}") //danh sach apply
    public ResponseEntity<?> getAppliedJobsFromJob(@Valid @PathVariable("job_id") Long jobId){
        try {
            List<AppliedJob> appliedJobs = appliedJobService.findByJobId(jobId);
            return ResponseEntity.ok().body(appliedJobs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{user_id}/{job_id}")
    public ResponseEntity<?> checkAppliedJob(
            @Valid @PathVariable("user_id") Long user_id,
            @Valid @PathVariable("job_id") Long job_id
    ){
        try {
            List<AppliedJob>appliedJobs = appliedJobService.checkAppliedJob(user_id, job_id);
            return ResponseEntity.ok().body(appliedJobs.stream()
                    .map(AppliedJobResponse::fromAppliedJob)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getAppliedJob(
            @Valid @PathVariable("id") Long id
    ){
        try {
            AppliedJob appliedJob = appliedJobService.getAppliedJob(id);
            return ResponseEntity.ok().body(AppliedJobResponse.fromAppliedJob(appliedJob));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAppliedJob(
            @Valid @PathVariable Long id,
            @Valid @RequestBody AppliedJobDTO appliedJobDTO
    ){
        try {
            AppliedJob existingAppliedJob = appliedJobService.updateAppliedJob(id, appliedJobDTO);
            return ResponseEntity.ok().body(AppliedJobResponse.fromAppliedJob(existingAppliedJob));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    //xoa mem is_active -> 0
    public ResponseEntity<String> deleteAppliedJob(@Valid @PathVariable Long id){
        try {
            appliedJobService.deleteApplied(id);
            return ResponseEntity.ok().body("delete Applied Job successfully with id = "+id);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
