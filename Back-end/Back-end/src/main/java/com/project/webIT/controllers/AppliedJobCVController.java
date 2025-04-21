package com.project.webIT.controllers;

import com.project.webIT.dtos.request.AppliedJobCVDTO;
import com.project.webIT.dtos.request.AppliedJobDTO;
import com.project.webIT.dtos.request.UserCVDTO;
import com.project.webIT.dtos.response.AppliedJobCVResponse;
import com.project.webIT.dtos.response.ObjectResponse;
import com.project.webIT.dtos.response.UserCVResponse;
import com.project.webIT.models.AppliedJobCV;
import com.project.webIT.models.User;
import com.project.webIT.models.UserCV;
import com.project.webIT.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/applied-cv")
@RequiredArgsConstructor
@Slf4j
public class AppliedJobCVController {
    private final AppliedJobCVServiceImpl appliedJobCVService;
    private final UserServiceImpl userService;
    private final CloudinaryServiceImpl cloudinaryService;
    private final FileServiceImpl fileService;

    @PostMapping("")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ObjectResponse<AppliedJobCVResponse>> addAppliedJobCV(
            @RequestBody AppliedJobCVDTO appliedJobCVDTO,
            @AuthenticationPrincipal User user
    ) throws Exception {
//        String extractedToken = authorizationHeader.substring(7);
//        User user = userServiceImpl.getUserDetailsFromToken(extractedToken);
        AppliedJobCV appliedJobCV = appliedJobCVService.createAppliedJobCV(appliedJobCVDTO);
        return ResponseEntity.ok(
                ObjectResponse.<AppliedJobCVResponse>builder()
                        .status(HttpStatus.OK)
                        .message("Add CV successfully")
                        .data(AppliedJobCVResponse.fromAppliedJobCV(appliedJobCV))
                        .build()
        );
    }
}
