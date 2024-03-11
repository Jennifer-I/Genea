package com.genea.controller;


import com.genea.dto.request.InitializePaymentRequest;
import com.genea.dto.response.InitializePaymentResponse;
import com.genea.dto.response.VerifyPaymentResponse;
import com.genea.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/initializePayment")
    public ResponseEntity<InitializePaymentResponse> initializePayment(@RequestBody InitializePaymentRequest request) {
        return ResponseEntity.ok(paymentService.initializePayment(request));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/verifyPayment/{reference}")
    public ResponseEntity<VerifyPaymentResponse> verifyPayment(@PathVariable String reference) {
        return ResponseEntity.ok(paymentService.verifyPayment(reference));
    }



}
