package com.project.webIT.controllers;

import com.project.webIT.dtos.request.UserPaymentDTO;
import com.project.webIT.dtos.response.*;
import com.project.webIT.models.User;
import com.project.webIT.models.UserPayment;
import com.project.webIT.repositories.UserPaymentRepository;
import com.project.webIT.services.UserPaymentServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @GetMapping("get/page")
    public ResponseEntity<ObjectResponse<DataListResponse<UserPaymentResponse>>> getPaymentPage (
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false, name = "status") String status,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "20", name = "limit") int limit
    ){
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("updatedAt").descending());
        Page<UserPaymentResponse> paymentResponses = userPaymentServiceImpl.managerPayment(keyword, status, pageRequest);
        List<UserPaymentResponse> userPaymentResponses = paymentResponses.getContent();

        DataListResponse<UserPaymentResponse> dataListResponse = DataListResponse.<UserPaymentResponse>builder()
                .dataList(userPaymentResponses)
                .totalData(paymentResponses.getTotalElements())
                .totalPages(paymentResponses.getTotalPages())
                .build();

        return ResponseEntity.ok(
                ObjectResponse.<DataListResponse<UserPaymentResponse>>builder()
                        .status(HttpStatus.OK)
                        .message("Lấy danh sách thanh toán thành công")
                        .data(dataListResponse)
                        .build()
        );
    }
}
