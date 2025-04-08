package com.project.webIT.controllers;

import com.project.webIT.dtos.jobs.JobFunctionDTO;
import com.project.webIT.models.JobFunction;
import com.project.webIT.response.ResponseObject;
import com.project.webIT.services.JobFunctionService;
import jakarta.validation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ResponseObject> createJobFunction(
            @Valid @RequestBody JobFunctionDTO jobFunctionDTO,
            BindingResult result
    ) throws Exception {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(
                    ResponseObject.builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .message(String.join(";",errorMessages))
                            .build()
            );
        }
        jobFunctionService.createJobFunction(jobFunctionDTO);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .message("Bạn đã thêm thành công ngành nghề "+jobFunctionDTO.getName())
                        .build()
        );
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getJobFunctions() throws Exception{
        List<JobFunction> jobFunctions = jobFunctionService.getAllJobFunctions();
        return ResponseEntity.ok().body(
                ResponseObject
                        .builder()
                        .message("Bạn đã lấy danh sách ngành nghề thành công")
                        .status(HttpStatus.OK)
                        .data(jobFunctions)
                        .build()
        );
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseObject> updateJobFunction(
            @Valid @PathVariable Long id,
            @Valid @RequestBody JobFunctionDTO jobFunctionDTO
    ) throws Exception {
        JobFunction newJobFunction = jobFunctionService.updateJobFunction(id,jobFunctionDTO);
        return ResponseEntity.ok().body(
                ResponseObject
                        .builder()
                        .message("Bạn đã cập ngành nghề "+newJobFunction.getName())
                        .status(HttpStatus.OK)
                        .data(newJobFunction)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseObject> deleteJobFunction(
            @Valid @PathVariable Long id
    ) throws Exception {
        jobFunctionService.deleteJobFunction(id);
        return ResponseEntity.ok().body(
                ResponseObject
                        .builder()
                        .message("Bạn đã xóa ngành nghề: "+id+" thành công")
                        .status(HttpStatus.OK)
                        .build()
        );
    }
}
