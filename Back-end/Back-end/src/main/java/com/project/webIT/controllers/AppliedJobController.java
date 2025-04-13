package com.project.webIT.controllers;

import com.project.webIT.dtos.request.AppliedJobDTO;
import com.project.webIT.helper.ValidationHelper;
import com.project.webIT.models.AppliedJob;
import com.project.webIT.dtos.response.ObjectResponse;
import com.project.webIT.dtos.response.AppliedJobResponse;
import com.project.webIT.models.User;
import com.project.webIT.services.AppliedJobServiceImpl;
import com.project.webIT.services.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/applied")
@RequiredArgsConstructor
public class AppliedJobController implements BaseController<AppliedJobDTO, Long>{
    private final AppliedJobServiceImpl appliedJobService;
    private final UserServiceImpl userService;

    @GetMapping("ok") //danh sach apply
    public ResponseEntity<ObjectResponse<List<AppliedJobResponse>>> getAppliedJobsFromUser(
            @Valid @RequestHeader("Authorization") String authorizationHeader
    )throws Exception{
        String extractedToken = authorizationHeader.substring(7);
        User user = userService.getUserDetailsFromToken(extractedToken);
        List<AppliedJob> appliedJobs = appliedJobService.getAppliedJobFromUser(user.getId());
        return ResponseEntity.ok().body(
                ObjectResponse.<List<AppliedJobResponse>>builder()
                        .status(HttpStatus.OK)
                        .message("get appliedJob successfully")
                        .data(appliedJobs.stream()
                                .map(AppliedJobResponse::fromAppliedJob)
                                .collect(Collectors.toList())).build()
        );
    }

    @GetMapping("job/{job_id}") //danh sach apply
    public ResponseEntity<ObjectResponse<List<AppliedJobResponse>>> getAppliedJobsFromJob(
            @Valid @PathVariable("job_id") Long jobId
    ){
            List<AppliedJob> appliedJobs = appliedJobService.findByJobId(jobId);
            List<AppliedJobResponse> appliedJobResponses = appliedJobs.stream()
                    .map(AppliedJobResponse::fromAppliedJob)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(
                    ObjectResponse.<List<AppliedJobResponse>>builder()
                            .status(HttpStatus.OK)
                            .message("get appliedJobList from job successfully")
                            .data(appliedJobResponses).build()
            );
    }

    @GetMapping("ok/{job_id}")
    public ResponseEntity<ObjectResponse<List<AppliedJobResponse>>> checkAppliedJob(
            @Valid @RequestHeader("Authorization") String authorizationHeader,
            @Valid @PathVariable("job_id") Long job_id
    ) throws Exception {
        String extractedToken = authorizationHeader.substring(7);
        User user = userService.getUserDetailsFromToken(extractedToken);
        List<AppliedJob>appliedJobs = appliedJobService.checkAppliedJob(user.getId(), job_id);
        List<AppliedJobResponse> appliedJobResponses = appliedJobs.stream()
                .map(AppliedJobResponse::fromAppliedJob)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(
                ObjectResponse.<List<AppliedJobResponse>>builder()
                        .message("get appliedJob from user successfully")
                        .status(HttpStatus.OK)
                        .data(appliedJobResponses).build()
        );
    }

    @Override
    @PostMapping("")
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

    @Override
    @PutMapping("{id}")
    public ResponseEntity<ObjectResponse<?>> update(Long id, AppliedJobDTO request, BindingResult result) throws Exception{
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

    @Override
    public ResponseEntity<ObjectResponse<?>> getAll() {
        return null;
    }

    @Override
    public ResponseEntity<ObjectResponse<?>> getById(Long id) throws Exception{
        AppliedJob appliedJob = appliedJobService.getAppliedJob(id);
        return ResponseEntity.ok().body(
                ObjectResponse.<AppliedJobResponse>builder()
                        .message("get appliedJob successfully: "+id)
                        .status(HttpStatus.OK)
                        .data(AppliedJobResponse.fromAppliedJob(appliedJob)).build()
        );
    }

    @Override
    public ResponseEntity<ObjectResponse<?>> deleteById(Long id) throws Exception{
        appliedJobService.deleteApplied(id);
        return ResponseEntity.ok().body(
                ObjectResponse.<Void>builder()
                        .message("delete appliedJob successfully: "+id)
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @Override
    public ResponseEntity<ObjectResponse<?>> deleteByListId(List<Long> listId) throws Exception{
        return null;
    }
}
