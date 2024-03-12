package com.genea.controller;


import com.genea.dto.request.InitializePaymentRequest;
import com.genea.dto.response.InitializePaymentResponse;
import com.genea.dto.response.TransactionResponse;
import com.genea.dto.response.VerifyPaymentResponse;
import com.genea.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/initializePayment")
    public ResponseEntity<InitializePaymentResponse> initializePayment(@RequestBody InitializePaymentRequest request) {
        return ResponseEntity.ok(transactionService.initializePayment(request));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/verifyPayment/{reference}")
    public ResponseEntity<VerifyPaymentResponse> verifyPayment(@PathVariable String reference) {
        return ResponseEntity.ok(transactionService.verifyPayment(reference));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllTransaction")
    public ResponseEntity<TransactionResponse> getTransaction() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getTransactionById/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable String id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }



}
