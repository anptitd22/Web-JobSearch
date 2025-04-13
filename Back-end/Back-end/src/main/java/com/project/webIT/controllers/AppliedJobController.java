package com.project.webIT.controllers;

import com.project.webIT.dtos.request.AppliedJobDTO;
import com.project.webIT.helper.ValidationHelper;
import com.project.webIT.models.AppliedJob;
import com.project.webIT.dtos.response.ObjectResponse;
import com.project.webIT.dtos.response.AppliedJobResponse;
import com.project.webIT.models.Company;
import com.project.webIT.models.User;
import com.project.webIT.services.AppliedJobServiceImpl;
import com.project.webIT.services.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/applied")
@RequiredArgsConstructor
@Slf4j
public class AppliedJobController{
    private final AppliedJobServiceImpl appliedJobService;
    private final UserServiceImpl userService;

    @GetMapping("user") //danh sach apply
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ObjectResponse<List<AppliedJobResponse>>> getAppliedJobsFromUser(
            @AuthenticationPrincipal User user
    ){
        List<AppliedJob> appliedJobEntities = appliedJobService.getAppliedJobFromUser(user.getId());
        return ResponseEntity.ok().body(
                ObjectResponse.<List<AppliedJobResponse>>builder()
                        .status(HttpStatus.OK)
                        .message("get appliedJob successfully")
                        .data(appliedJobEntities.stream()
                                .map(AppliedJobResponse::fromAppliedJob)
                                .collect(Collectors.toList())).build()
        );
    }

    @GetMapping("job/{job_id}") //danh sach apply
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<ObjectResponse<List<AppliedJobResponse>>> getAppliedJobsFromJob(
            @Valid @PathVariable("job_id") Long jobId,
            @AuthenticationPrincipal Company company
    ){
            List<AppliedJob> appliedJobEntities = appliedJobService.findByJobId(jobId);
            List<AppliedJobResponse> appliedJobResponses = appliedJobEntities.stream()
                    .map(AppliedJobResponse::fromAppliedJob)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(
                    ObjectResponse.<List<AppliedJobResponse>>builder()
                            .status(HttpStatus.OK)
                            .message("get appliedJobList from job successfully")
                            .data(appliedJobResponses).build()
            );
    }

    @GetMapping("user/{job_id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ObjectResponse<List<AppliedJobResponse>>> checkAppliedJob(
            @Valid @PathVariable("job_id") Long job_id,
            @AuthenticationPrincipal User user
    ){
        List<AppliedJob> appliedJobEntities = appliedJobService.checkAppliedJob(user.getId(), job_id);
        List<AppliedJobResponse> appliedJobResponses = appliedJobEntities.stream()
                .map(AppliedJobResponse::fromAppliedJob)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(
                ObjectResponse.<List<AppliedJobResponse>>builder()
                        .message("get appliedJob from user successfully")
                        .status(HttpStatus.OK)
                        .data(appliedJobResponses).build()
        );
    }

    @PostMapping("")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ObjectResponse<?>> create(
            @Valid @RequestBody AppliedJobDTO appliedJobDTO,
            BindingResult result
    ) throws Exception {
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(
                    ObjectResponse.<Void>builder()
                            .message(ValidationHelper.extractDetailedErrorMessages(result))
                            .status(HttpStatus.BAD_REQUEST)
                            .build()
            );
        }
        AppliedJob appliedJob = appliedJobService.createAppliedJob(appliedJobDTO);
        return ResponseEntity.ok(
                ObjectResponse.<AppliedJobResponse>builder()
                        .status(HttpStatus.OK)
                        .message("add jobFunction successfully")
                        .data(AppliedJobResponse.fromAppliedJob(appliedJob)).build()
        );
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ObjectResponse<?>> update(
            @Valid @PathVariable("id")Long id,
            @Valid @RequestBody AppliedJobDTO request,
            BindingResult result
    ) throws Exception{
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(
                    ObjectResponse.builder()
                            .message(ValidationHelper.extractDetailedErrorMessages(result))
                            .status(HttpStatus.BAD_REQUEST)
                            .build()
            );
        }
        AppliedJob existingAppliedJob = appliedJobService.updateAppliedJob(id, request);
        return ResponseEntity.ok().body(
                ObjectResponse.<AppliedJobResponse>builder()
                        .status(HttpStatus.OK)
                        .message("update appliedJob successfully: "+id)
                        .data(AppliedJobResponse.fromAppliedJob(existingAppliedJob)).build()
        );
    }

    @GetMapping("get")
    public ResponseEntity<ObjectResponse<?>> getAll() {
        return null;
    }

    @GetMapping("get/{id}")
    public ResponseEntity<ObjectResponse<?>> getById(@Valid @PathVariable("id") Long id) throws Exception{
        AppliedJob appliedJob = appliedJobService.getAppliedJob(id);
        return ResponseEntity.ok().body(
                ObjectResponse.<AppliedJobResponse>builder()
                        .message("get appliedJob successfully: "+id)
                        .status(HttpStatus.OK)
                        .data(AppliedJobResponse.fromAppliedJob(appliedJob)).build()
        );
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ObjectResponse<?>> deleteById(@Valid @PathVariable("id") Long id) throws Exception{
        appliedJobService.deleteApplied(id);
        return ResponseEntity.ok().body(
                ObjectResponse.<Void>builder()
                        .message("delete appliedJob successfully: "+id)
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @DeleteMapping("delete")
    public ResponseEntity<ObjectResponse<?>> deleteByListId(List<Long> listId) throws Exception{
        return null;
    }
}
