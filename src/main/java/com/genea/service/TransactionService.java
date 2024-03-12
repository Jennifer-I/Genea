package com.genea.service;


import com.genea.dto.request.InitializePaymentRequest;
import com.genea.dto.response.InitializePaymentResponse;
import com.genea.dto.response.TransactionResponse;
import com.genea.dto.response.VerifyPaymentResponse;
import org.springframework.stereotype.Service;



@Service

public interface TransactionService {


    InitializePaymentResponse initializePayment(InitializePaymentRequest request);

    VerifyPaymentResponse verifyPayment(String reference);

    TransactionResponse getAllTransactions();

    TransactionResponse getTransactionById(String id);
}
