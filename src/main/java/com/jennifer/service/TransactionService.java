package com.jennifer.service;


import com.jennifer.dto.request.InitializePaymentRequest;
import com.jennifer.dto.response.InitializePaymentResponse;
import com.jennifer.dto.response.TransactionResponse;
import com.jennifer.dto.response.VerifyPaymentResponse;
import org.springframework.stereotype.Service;



@Service

public interface TransactionService {


    InitializePaymentResponse initializePayment(InitializePaymentRequest request);

    VerifyPaymentResponse verifyPayment(String reference);

    TransactionResponse getAllTransactions();

    TransactionResponse getTransactionById(String id);
}
