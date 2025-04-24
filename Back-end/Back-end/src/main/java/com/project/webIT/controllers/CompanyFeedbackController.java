package com.project.webIT.controllers;

import com.project.webIT.dtos.request.CompanyFeedbackAcceptDTO;
import com.project.webIT.dtos.request.CompanyFeedbackRefuseDTO;
import com.project.webIT.dtos.response.CompanyFeedbackAcceptResponse;
import com.project.webIT.dtos.response.CompanyFeedbackRefuseResponse;
import com.project.webIT.dtos.response.ObjectResponse;
import com.project.webIT.helper.ValidationHelper;
import com.project.webIT.models.CompanyFeedbackAccept;
import com.project.webIT.models.CompanyFeedbackRefuse;
import com.project.webIT.services.IService.CompanyFeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/feedback")
public class CompanyFeedbackController {

    private final CompanyFeedbackService companyFeedbackService;

    @PostMapping("accept")
    @PreAuthorize("hasRole('COMPANY')")
    ResponseEntity<ObjectResponse<?>> createFeedbackAccept(
            @Valid @RequestBody CompanyFeedbackAcceptDTO companyFeedbackAcceptDTO,
            BindingResult result
    )throws Exception{
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(
                    ObjectResponse.<Void>builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .message(ValidationHelper.extractDetailedErrorMessages(result))
                            .build()
            );
        }
        CompanyFeedbackAccept companyFeedbackAccept = companyFeedbackService.createFeedbackAccept(companyFeedbackAcceptDTO);
        return ResponseEntity.ok().body(
                ObjectResponse.<CompanyFeedbackAcceptResponse>builder()
                        .status(HttpStatus.OK)
                        .message("feedback successfully")
                        .data(CompanyFeedbackAcceptResponse.fromCompanyFeedbackAccept(companyFeedbackAccept)).build()
        );
    }

    @GetMapping("accept/{id}")
    ResponseEntity<ObjectResponse<CompanyFeedbackAcceptResponse>> getFeedbackAccept(
            @PathVariable("id") Long id
    ) throws Exception{
        return ResponseEntity.ok().body(
                ObjectResponse.<CompanyFeedbackAcceptResponse>builder()
                        .data(CompanyFeedbackAcceptResponse.fromCompanyFeedbackAccept(companyFeedbackService.getFeedbackAccept(id)))
                        .status(HttpStatus.OK)
                        .message("get feedback successfully")
                        .build()
        );
    }

    @PostMapping("refuse")
    @PreAuthorize("hasRole('COMPANY')")
    ResponseEntity<ObjectResponse<?>> createFeedbackRefuse(
                @Valid @RequestBody CompanyFeedbackRefuseDTO companyFeedbackRefuseDTO,
                BindingResult result
    )throws Exception{
        if(result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    ObjectResponse.<Void>builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .message(ValidationHelper.extractDetailedErrorMessages(result))
                            .build()
            );
        }
        CompanyFeedbackRefuse companyFeedbackRefuse = companyFeedbackService.createFeedbackRefuse(companyFeedbackRefuseDTO);
        return ResponseEntity.ok().body(
                ObjectResponse.<CompanyFeedbackRefuseResponse>builder()
                        .status(HttpStatus.OK)
                        .message("feedback successfully")
                        .data(CompanyFeedbackRefuseResponse.fromCompanyFeedbackRefuse(companyFeedbackRefuse)).build()
        );
    }

    @GetMapping("refuse/{id}")
    ResponseEntity<ObjectResponse<?>> getFeedbackRefuse(
            @PathVariable("id") Long id
    ) throws Exception{
        return ResponseEntity.ok().body(
                ObjectResponse.<CompanyFeedbackRefuseResponse>builder()
                        .data(CompanyFeedbackRefuseResponse.fromCompanyFeedbackRefuse(companyFeedbackService.getFeedbackRefuse(id)))
                        .status(HttpStatus.OK)
                        .message("get feedback successfully")
                        .build()
        );
    }
}
