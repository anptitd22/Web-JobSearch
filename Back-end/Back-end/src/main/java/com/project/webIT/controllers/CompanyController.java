package com.project.webIT.controllers;

import com.project.webIT.dtos.request.CompanyDTO;
import com.project.webIT.dtos.request.CompanyLoginDTO;
import com.project.webIT.dtos.response.*;
import com.project.webIT.helper.FileHelper;
import com.project.webIT.helper.ValidationHelper;
import com.project.webIT.models.Company;
import com.project.webIT.models.Job;
import com.project.webIT.services.CloudinaryServiceImpl;
import com.project.webIT.services.CompanyServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/companies")
@RequiredArgsConstructor
public class CompanyController{
    private final CompanyServiceImpl companyServiceImpl;
    private final CloudinaryServiceImpl cloudinaryServiceImpl;

    @PostMapping("login")
    public ResponseEntity<ObjectResponse<String>> loginCompany(
            @Valid @RequestBody CompanyLoginDTO companyLoginDTO,
            BindingResult result
    ) throws Exception {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    ObjectResponse.<String>builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .message(ValidationHelper.extractDetailedErrorMessages(result))
                            .data(null)
                            .build()
            );
        }
        String token = companyServiceImpl.loginCompany(companyLoginDTO);
        return ResponseEntity.ok(
                ObjectResponse.<String>builder()
                        .status(HttpStatus.OK)
                        .message("Bạn đã đăng nhập thành công")
                        .data(token)
                        .build()
        );
    }

    @PostMapping(value = "uploads/{companyId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ObjectResponse<?>> postImage(
            @PathVariable("companyId") Long companyId,
            @RequestPart("files") List<MultipartFile> files
    ) throws Exception {
        if (files == null) {
            files = new ArrayList<>();
        }
        if (files.size() > 1) {
            return ResponseEntity.badRequest().body(
                    ObjectResponse.<String>builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .message("You can only upload maximum 1 images")
                            .data(null)
                            .build()
            );
        }

        MultipartFile file = files.getFirst();
        if (file.getSize() == 0) {
            return ResponseEntity.badRequest().body(
                    ObjectResponse.<String>builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .message("upload fail")
                            .data(null)
                            .build()
            );
        }

        if (file.getSize() > 10 * 1024 * 1024) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                    .body(ObjectResponse.<String>builder()
                            .status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .message("File is too large, Maximum is 10MB")
                            .data(null)
                            .build());
        }

        if (FileHelper.isImageFile(file)) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                    .body(ObjectResponse.<String>builder()
                            .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .message("File must be an image")
                            .data(null)
                            .build());
        }

        String publicId = companyServiceImpl.getPublicId(companyId);
        if (!publicId.isEmpty()){
            return ResponseEntity.ok().body(
                    ObjectResponse.<Map>builder()
                            .status(HttpStatus.OK)
                            .message("update Image successfully")
                            .data(cloudinaryServiceImpl.updateImage(publicId,file)).build()
            );
        }

        return ResponseEntity.ok(
                ObjectResponse.<Map>builder()
                        .status(HttpStatus.OK)
                        .message("add Image successfully")
                        .data(cloudinaryServiceImpl.upload(file))
                        .build()
        );
    }

    @GetMapping("details")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<ObjectResponse<CompanyResponse>> getCompanyDetail(
            @AuthenticationPrincipal Company company
    ){
        return ResponseEntity.ok(
                ObjectResponse.<CompanyResponse>builder()
                        .status(HttpStatus.OK)
                        .message("Lấy thông tin công ty thành công")
                        .data(CompanyResponse.fromCompany(company))
                        .build()
        );
    }

    @GetMapping("public/{companyId}/get/jobs")
    public ResponseEntity<ObjectResponse<JobListResponse>> getJobsFromCompanyPublic(
            @Valid @PathVariable("companyId") Long companyId,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0", name = "job_function_id") Long jobFunctionId,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "20", name = "limit") int limit,
            @RequestParam(defaultValue = "date_desc", name = "sort_by") String sortBy
    ){
        Sort sort = Sort.by("updatedAt").descending();
        PageRequest pageRequest = PageRequest.of(page, limit, sort);
        Page<JobResponse> jobPage = companyServiceImpl.getJobs(companyId, keyword, jobFunctionId, pageRequest);
        JobListResponse jobListResponse = JobListResponse.builder()
                .jobs(jobPage.getContent())
                .totalPages(jobPage.getTotalPages())
                .totalJob(jobPage.getTotalElements())
                .build();

        return ResponseEntity.ok(
                ObjectResponse.<JobListResponse>builder()
                        .status(HttpStatus.OK)
                        .message("Lấy danh sách việc làm thành công")
                        .data(jobListResponse)
                        .build()
        );
    }

    @GetMapping("private/get/jobs")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<ObjectResponse<JobListResponse>> getJobsFromCompanyPrivate(
            @AuthenticationPrincipal Company company,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0", name = "job_function_id") Long jobFunctionId,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "20", name = "limit") int limit,
            @RequestParam(defaultValue = "date_desc", name = "sort_by") String sortBy
    ){
        Sort sort = Sort.by("updatedAt").descending();
        PageRequest pageRequest = PageRequest.of(page, limit, sort);
        Page<JobResponse> jobPage = companyServiceImpl.getJobs(company.getId(), keyword, jobFunctionId, pageRequest);
        JobListResponse jobListResponse = JobListResponse.builder()
                .jobs(jobPage.getContent())
                .totalPages(jobPage.getTotalPages())
                .totalJob(jobPage.getTotalElements())
                .build();

        return ResponseEntity.ok(
                ObjectResponse.<JobListResponse>builder()
                        .status(HttpStatus.OK)
                        .message("Lấy danh sách việc làm thành công")
                        .data(jobListResponse)
                        .build()
        );
    }

    @GetMapping("public/get/page")
    public ResponseEntity<ObjectResponse<CompanyListResponse>> getCompany(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0", name = "industry_id") Long industryId,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "20", name = "limit") int limit
    ) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("name").descending());
        Page<CompanyResponse> companyPage = companyServiceImpl.getAllCompanies(keyword, industryId, pageRequest);
        List<CompanyResponse> companies = companyPage.getContent();

        CompanyListResponse companyListResponse = CompanyListResponse.builder()
                .companies(companies)
                .totalPages(companyPage.getTotalPages())
                .totalCompanies(companyPage.getTotalElements())
                .build();

        return ResponseEntity.ok(
                ObjectResponse.<CompanyListResponse>builder()
                        .status(HttpStatus.OK)
                        .message("Lấy danh sách công ty thành công")
                        .data(companyListResponse)
                        .build()
        );
    }

    @PostMapping("")
    public ResponseEntity<ObjectResponse<?>> createCompany(
            @Valid @RequestBody CompanyDTO request,
            BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    ObjectResponse.<Company>builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .message(ValidationHelper.extractDetailedErrorMessages(result))
                            .data(null)
                            .build()
            );
        }

        Company newCompany = companyServiceImpl.createCompany(request);
        return ResponseEntity.ok(
                ObjectResponse.<Company>builder()
                        .status(HttpStatus.OK)
                        .message("Tạo công ty thành công")
                        .data(newCompany)
                        .build()
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<ObjectResponse<?>> update(
            @Valid @PathVariable("id") Long id,
            @Valid @RequestBody CompanyDTO request,
            BindingResult result
    ) throws Exception {
        Company updated = companyServiceImpl.updateCompany(id, request);
        return ResponseEntity.ok(
                ObjectResponse.<CompanyResponse>builder()
                        .status(HttpStatus.OK)
                        .message("Cập nhật công ty thành công")
                        .data(CompanyResponse.fromCompany(updated))
                        .build()
        );
    }

    @GetMapping("get")
    public ResponseEntity<ObjectResponse<?>> getAll() {
        return null;
    }

    @GetMapping("get/{id}")
    public ResponseEntity<ObjectResponse<CompanyResponse>> getById(
            @Valid @PathVariable("id") Long id
    ) throws Exception {
        Company existingCompany = companyServiceImpl.getCompanyById(id);
        return ResponseEntity.ok(
                ObjectResponse.<CompanyResponse>builder()
                        .status(HttpStatus.OK)
                        .message("Lấy công ty thành công")
                        .data(CompanyResponse.fromCompany(existingCompany))
                        .build()
        );
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ObjectResponse<?>> deleteById(@Valid @PathVariable("id") Long id) throws Exception {
        companyServiceImpl.deleteCompany(id);
        return ResponseEntity.ok(
                ObjectResponse.<String>builder()
                        .status(HttpStatus.OK)
                        .message("delete Company successfully with id = " + id)
                        .data(null)
                        .build()
        );
    }

    @DeleteMapping("delete")
    public ResponseEntity<ObjectResponse<?>> deleteByListId(List<Long> listId) throws Exception {
        return null;
    }
}
