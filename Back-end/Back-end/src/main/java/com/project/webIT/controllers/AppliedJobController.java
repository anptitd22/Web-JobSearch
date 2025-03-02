package com.project.webIT.controllers;

import com.project.webIT.dtos.appliedJob.AppliedJobDTO;
import com.project.webIT.exception.DataNotFoundException;
import com.project.webIT.models.AppliedJob;
import com.project.webIT.models.Job;
import com.project.webIT.response.appliedJob.AppliedJobResponse;
import com.project.webIT.services.AppliedJobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/applied")
@RequiredArgsConstructor
public class AppliedJobController {
    private final AppliedJobService appliedJobService;

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

    @GetMapping("user/{user_id}") //danh sach apply
    public ResponseEntity<?> getAppliedJobsFromUser(@Valid @PathVariable("user_id") Long userId){
        try {
            List<AppliedJob> appliedJobs = appliedJobService.findByUserId(userId);
            return ResponseEntity.ok().body(appliedJobs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("job/{job_id}") //danh sach apply
    public ResponseEntity<?> getAppliedJobsFromJob(@Valid @PathVariable("job_id") Long jobId){
        try {
            List<Job> appliedJobs = appliedJobService.findByJobId(jobId);
            return ResponseEntity.ok().body(appliedJobs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAppliedJob(@Valid @PathVariable("id") Long id){
        try {
            AppliedJob existingAppliedJob = appliedJobService.getAppliedJob(id);
            return ResponseEntity.ok().body(AppliedJobResponse.fromAppliedJob(existingAppliedJob));
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
