package com.project.webIT.controllers;

import com.project.webIT.dtos.request.UserPaymentDTO;
import com.project.webIT.models.User;
import com.project.webIT.models.UserPayment;
import com.project.webIT.repositories.UserPaymentRepository;
import com.project.webIT.dtos.response.ObjectResponse;
import com.project.webIT.dtos.response.UserPaymentResponse;
import com.project.webIT.services.UserPaymentServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/payments")
@RequiredArgsConstructor
public class UserPaymentController {

    private final UserPaymentServiceImpl userPaymentServiceImpl;

    @PostMapping("")
    public ResponseEntity<ObjectResponse<UserPaymentResponse>> userPayment(
            @Valid @RequestBody UserPaymentDTO userPaymentDTO
    ) throws Exception {
        UserPayment userPayment = userPaymentServiceImpl.createBill(userPaymentDTO);

        return ResponseEntity.ok(
                ObjectResponse.<UserPaymentResponse>builder()
                        .status(HttpStatus.OK)
                        .message("Payment information retrieved successfully")
                        .data(UserPaymentResponse.fromUserPayment(userPayment))
                        .build()
        );
    }

    @GetMapping("get")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ObjectResponse<List<UserPaymentResponse>>> getUserPayment(
            @AuthenticationPrincipal User user
    ){
        List<UserPayment> userPaymentList = userPaymentServiceImpl.findByUserId(user.getId());
        return ResponseEntity.ok(
                ObjectResponse.<List<UserPaymentResponse>>builder()
                        .status(HttpStatus.OK)
                        .message("Payment information retrieved successfully")
                        .data(userPaymentList.stream()
                                .map(UserPaymentResponse::fromUserPayment)
                                .collect(Collectors.toList()))
                        .build()
        );
    }
}
