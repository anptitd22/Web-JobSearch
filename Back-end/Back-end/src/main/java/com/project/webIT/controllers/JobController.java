package com.project.webIT.controllers;

import com.github.javafaker.Faker;
import com.project.webIT.dtos.jobs.JobDTO;
import com.project.webIT.dtos.jobs.JobImageDTO;
import com.project.webIT.models.Job;
import com.project.webIT.models.JobImage;
import com.project.webIT.response.jobs.JobListResponse;
import com.project.webIT.response.jobs.JobResponse;
import com.project.webIT.services.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@RestController
@RequestMapping("${api.prefix}/jobs")
//@Validated
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @PostMapping("")
    public ResponseEntity<?> createJob(
            @Valid @RequestBody JobDTO jobDTO,
            BindingResult result
    ){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Job newJob = jobService.createJob(jobDTO);
            return ResponseEntity.ok(newJob);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "uploads/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(
            @PathVariable ("id") Long id,
            @ModelAttribute ("files") ArrayList<MultipartFile> files //not List - cay lam roi
    ){
        try{
            Job existingJob = jobService.getJobById(id);
            if (files == null) {
                files = new ArrayList<>(); //truong hop khong tai file
            }
            if(files.size() >= JobImage.MAXIMUM_IMAGES_PER_JOB){
                return ResponseEntity.badRequest().body("You can only upload maximum 5 images");
            }
            List<JobImage> jobImages = new ArrayList<>();
            for (MultipartFile file : files) {
                if (file.getSize() == 0) { //truong hop file rong
                    continue;
                }
                if (file.getSize() > 10 * 1024 * 1024) { //kiem tra kich thuoc file anh
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).
                            body("File is too large, Maximum is 10MB");
                }
                String contentType = file.getContentType(); //kiem tra dinh dang file anh
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).
                            body("File must be an image");
                }
                //Luu file va cap nhat duong dan trong DTO
                String filename = storeFile(file); //Thay the ham
                //Luu vao doi tuong job trong db -> lam sau
                //Luu vao bang job_image
                JobImage jobImage = jobService.createJobImage(existingJob.getId(), JobImageDTO.builder()
                        .imageUrl(filename)
                        .build());
                jobImages.add(jobImage);
                existingJob.setJobImages(jobImages);
            }
            return ResponseEntity.ok().body("load successfully:"+"\n"+jobImages);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file)throws IOException{
        if(!isImageFile(file) || file.getOriginalFilename() == null){
            throw new IOException("valid image format");
        }
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())); //xoa ten file goc
        //Them UUID vao truoc ten file de dam bao ten file la duy nhat khong de nhau
        String uniqueFilename = UUID.randomUUID().toString()+"_"+filename;
        //Duong dan den thu muc chua file
        Path uploadDir = Paths.get("uploads");
        //kiem tra va tao thu muc neu no khong ton tai
        if(!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        //Duong dan day du den file
        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        //Sao chep file vao thu muc chinh
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING); //sao chep neu ton tai thi thay the
        return uniqueFilename;
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable String imageName) {
        try{
            java.nio.file.Path imagePath = Paths.get("Back-end/uploads/"+imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());
            if(resource.exists()){
                System.out.println("File found: " + imageName);
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            }else{
                System.out.println("File not found: " + imageName);
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                        .body(new UrlResource(Paths.get("Back-end/uploads/notfound404.jpg").toUri()));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Error accessing file: " + e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<JobListResponse> getJobs(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0", name = "job_function_id") Long jobFunctionId,
            @RequestParam(defaultValue = "0", name= "page")   int page,
            @RequestParam(defaultValue = "20", name = "limit")  int limit,
            @RequestParam(defaultValue = "date_desc", name="sort_by") String sortBy
    ){
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
        System.out.println("Sort Query: " + sort);
        //Tao Pageable tu thong tin trang va gioi han
        PageRequest pageRequest = PageRequest.of(page, limit, sort);
        //Lay tong page
        Page<JobResponse> jobPage = jobService.getAllJobs(keyword, jobFunctionId, pageRequest);
        int totalPages = jobPage.getTotalPages();
        List<JobResponse> jobs = jobPage.getContent();
        return ResponseEntity.ok(JobListResponse
                .builder()
                .jobs(jobs)
                .totalPages(totalPages)
                .totalJob(jobPage.getTotalElements())
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJob(
            @Valid @PathVariable("id") Long id){
        try{
            Job existingjob = jobService.getJobById(id);
            return ResponseEntity.ok().body(JobResponse.fromJob(existingjob));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateJob(
            @Valid @PathVariable Long id,
            @Valid @RequestBody JobDTO jobDTO
    ){
        try {
            Job newjob = jobService.updateJob(id, jobDTO);
            return ResponseEntity.ok().body(
                    JobResponse.fromJob(newjob)); //time
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @PreAuthorize("hasRole('USER') or hasRole('COMPANY')")
//    @PutMapping("{id}/views")
//    public ResponseEntity<?> increaseView(@PathVariable Long id, Authentication authentication){
//        try{
//            Job job = jobService.increaseViews(id, authentication);
//            return ResponseEntity.ok(JobResponse.fromJob(job));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @DeleteMapping("/temporary/{id}") //xoa mem
    @Transactional
    private ResponseEntity<String> endJob(@Valid @PathVariable Long id){
        try {
            jobService.overJob(id);
            return ResponseEntity.ok().body(
                    String.format("over Job successfully with id = "+id)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}") //xoa cung
    @Transactional
    //xoa mem is_active -> 0
    public ResponseEntity<String> deleteJob(@Valid @PathVariable Long id){
        jobService.deleteJob(id);
        return ResponseEntity.ok().body("delete Job successfully with id = "+id);
    }

//    @PostMapping("/generateFakeJob")
    public ResponseEntity<String> generateFakeJob (){
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
            try {
                jobService.createJob(jobDTO);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.ok().body("Fake Job created successfully");
    }
}