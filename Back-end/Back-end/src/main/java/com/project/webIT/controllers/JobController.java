package com.project.webIT.controllers;

import com.github.javafaker.Faker;
import com.project.webIT.dtos.request.JobDTO;
import com.project.webIT.helper.ValidationHelper;
import com.project.webIT.models.Job;
import com.project.webIT.dtos.response.ObjectResponse;
import com.project.webIT.dtos.response.JobListResponse;
import com.project.webIT.dtos.response.JobResponse;
import com.project.webIT.services.JobServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/jobs")
//@Validated
@RequiredArgsConstructor
public class JobController implements BaseController<JobDTO, Long>{
    private final JobServiceImpl jobService;

    @GetMapping("page")
    public ResponseEntity<ObjectResponse<?>> getJobs(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0", name = "job_function_id") Long jobFunctionId,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "20", name = "limit") int limit,
            @RequestParam(defaultValue = "date_desc", name = "sort_by") String sortBy
    ) {
        Sort sort = switch (sortBy) {
            case "date_asc" -> Sort.by("createdAt").ascending();
            case "date_desc" -> Sort.by("createdAt").descending();
            case "salary_asc" -> Sort.by(Sort.Order.asc("salaryNumeric").nullsFirst());
            case "salary_desc" -> Sort.by(Sort.Order.desc("salaryNumeric").nullsFirst());
            case "popularity_asc" -> Sort.by("view").ascending();
            case "popularity_desc" -> Sort.by("view").descending();
            case "name_asc" -> Sort.by("name").ascending();
            case "name_desc" -> Sort.by("name").descending();
            case "update_desc" -> Sort.by("updatedAt").descending();
            case "update_asc" -> Sort.by("updatedAt").ascending();
            default -> Sort.by("createdAt").descending();
        };

        PageRequest pageRequest = PageRequest.of(page, limit, sort);
        Page<JobResponse> jobPage = jobService.getAllJobs(keyword, jobFunctionId, pageRequest);

        JobListResponse jobListResponse = JobListResponse.builder()
                .jobs(jobPage.getContent())
                .totalPages(jobPage.getTotalPages())
                .totalJob(jobPage.getTotalElements())
                .build();

        return ResponseEntity.ok(
                ObjectResponse.<JobListResponse>builder()
                        .status(HttpStatus.OK)
                        .message("Jobs retrieved successfully")
                        .data(jobListResponse)
                        .build()
        );
    }
//    @PostMapping("/generateFakeJob")
    public ResponseEntity<String> generateFakeJob () throws Exception {
        Faker faker = new Faker();
        for(int i = 0; i <= 100000; i++){
            String jobName = faker.job().title();
            if (jobService.existByName(jobName)){
                continue;
            }
            int a = faker.number().numberBetween(1000,50000);
            int b = faker.number().numberBetween(a+1000,90000);
            JobDTO jobDTO = JobDTO.builder()
                    .name(jobName)
                    .salary(Integer.toString(a)+"-"+Integer.toString(b))
                    .salaryNumeric((float)((a+b)*1.0/2.0))
                    .jobLocations(faker.address().city())
                    .description(faker.lorem().sentence(100,500))
                    .jobFunctionId((long)faker.number().numberBetween(1,7))
                    .companyId((long)faker.number().numberBetween(1,5))
                    .typeOfWork(faker.job().field())
                    .endAt(LocalDateTime.now())
                    .build();
            jobService.createJob(jobDTO);
        }
        return ResponseEntity.ok().body("Fake Job created successfully");
    }

    @Override
    @PostMapping("")
    public ResponseEntity<ObjectResponse<?>> create(JobDTO request, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    ObjectResponse.<Void>builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .message(ValidationHelper.extractDetailedErrorMessages(result))
                            .build()
            );
        }
        Job newJob = jobService.createJob(request);
        return ResponseEntity.ok(
                ObjectResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Job created successfully")
                        .data(JobResponse.fromJob(newJob))
                        .build()
        );
    }

    @Override
    @PutMapping("{id}")
    public ResponseEntity<ObjectResponse<?>> update(Long id, JobDTO request, BindingResult result) throws Exception {
        Job updatedJob = jobService.updateJob(id, request);
        return ResponseEntity.ok(
                ObjectResponse.<JobResponse>builder()
                        .status(HttpStatus.OK)
                        .message("Job updated successfully")
                        .data(JobResponse.fromJob(updatedJob))
                        .build()
        );
    }

    @Override
    public ResponseEntity<ObjectResponse<?>> getAll() {
        return null;
    }

    @Override
    public ResponseEntity<ObjectResponse<?>> getById(Long id) throws Exception {
        Job existingJob = jobService.getJobById(id);
        return ResponseEntity.ok(
                ObjectResponse.<JobResponse>builder()
                        .status(HttpStatus.OK)
                        .message("Job retrieved successfully")
                        .data(JobResponse.fromJob(existingJob))
                        .build()
        );
    }

    @Override
    public ResponseEntity<ObjectResponse<?>> deleteById(Long id) throws Exception {
        jobService.overJob(id);
        return ResponseEntity.ok(
                ObjectResponse.<Void>builder()
                        .status(HttpStatus.OK)
                        .message("Job ended successfully with ID = " + id)
                        .data(null)
                        .build()
        );
    }

    @Override
    public ResponseEntity<ObjectResponse<?>> deleteByListId(List<Long> listId) throws Exception {
        return null;
    }
}