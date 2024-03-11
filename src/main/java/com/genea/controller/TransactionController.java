package com.genea.controller;


import com.genea.dto.request.InitializeTransactionRequest;
import com.genea.dto.response.InitializeTransactionResponse;
import com.genea.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/initializeTransaction")
    public ResponseEntity<InitializeTransactionResponse> initializeTransaction(@RequestBody InitializeTransactionRequest request) {
        return ResponseEntity.ok(transactionService.initializePayment(request));
    }

}
