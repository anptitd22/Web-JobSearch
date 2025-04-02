package com.project.webIT.controllers;

import com.project.webIT.dtos.users.UserPaymentDTO;
import com.project.webIT.models.UserPayment;
import com.project.webIT.repositories.UserPaymentRepository;
import com.project.webIT.response.users.UserPaymentResponse;
import com.project.webIT.services.UserPaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/payments")
@RequiredArgsConstructor
public class UserPaymentController {
    private final UserPaymentRepository userPaymentRepository;
    private final UserPaymentService userPaymentService;
    @PostMapping("")
    public ResponseEntity<?> userPayment (
            @Valid @RequestBody UserPaymentDTO userPaymentDTO
    ){
        try{
            UserPayment userPayment = userPaymentService.createBill(userPaymentDTO);
            return ResponseEntity.ok().body(UserPaymentResponse.fromUserPayment(userPayment));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
