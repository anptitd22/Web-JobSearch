package com.project.webIT.controllers;

import com.project.webIT.dtos.request.JobFunctionDTO;
import com.project.webIT.helper.ValidationHelper;
import com.project.webIT.models.JobFunction;
import com.project.webIT.dtos.response.ObjectResponse;
import com.project.webIT.services.JobFunctionServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/functions")
//@Validated
//Dependency Injection
@RequiredArgsConstructor
public class JobFunctionController{

    private final JobFunctionServiceImpl jobFunctionService;

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ObjectResponse<?>> create(
            @RequestBody JobFunctionDTO request,
            BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    ObjectResponse.<Void>builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .message(ValidationHelper.extractDetailedErrorMessages(result))
                            .data(null)
                            .build()
            );
        }

        jobFunctionService.createJobFunction(request);

        return ResponseEntity.ok(
                ObjectResponse.<Void>builder()
                        .status(HttpStatus.OK)
                        .message("Job function created successfully")
                        .data(null)
                        .build()
        );
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ObjectResponse<?>> update(
            @Valid @PathVariable("id") Long id,
            @Valid @RequestBody JobFunctionDTO request,
            BindingResult result
    ) throws Exception {
        JobFunction updatedJobFunction = jobFunctionService.updateJobFunction(id, request);

        return ResponseEntity.ok(
                ObjectResponse.<JobFunction>builder()
                        .status(HttpStatus.OK)
                        .message("Job function updated successfully")
                        .data(updatedJobFunction)
                        .build()
        );
    }

    @GetMapping("get")
    public ResponseEntity<ObjectResponse<?>> getAll() {
        List<JobFunction> jobFunctionEntities = jobFunctionService.getAllJobFunctions();

        return ResponseEntity.ok(
                ObjectResponse.<List<JobFunction>>builder()
                        .status(HttpStatus.OK)
                        .message("Job functions retrieved successfully")
                        .data(jobFunctionEntities)
                        .build()
        );
    }

    @GetMapping("get/{id}")
    public ResponseEntity<ObjectResponse<?>> getById(@PathVariable("id") Long id) throws Exception {
        return null;
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ObjectResponse<?>> deleteById(@PathVariable("id") Long id) throws Exception {
        jobFunctionService.deleteJobFunction(id);

        return ResponseEntity.ok(
                ObjectResponse.<Void>builder()
                        .status(HttpStatus.OK)
                        .message("Job function deleted successfully")
                        .data(null)
                        .build()
        );
    }

    @DeleteMapping("delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ObjectResponse<?>> deleteByListId(List<Long> listId) throws Exception {
        return null;
    }
}
