package com.jennifer.config;

import com.jennifer.dto.request.InitializePaymentRequest;
import com.jennifer.dto.response.InitializePaymentResponse;
import com.jennifer.dto.response.TransactionResponse;
import com.jennifer.dto.response.VerifyPaymentResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface PayStackClient {

    @RequestLine("POST /transaction/initialize")
    @Headers("Content-Type: application/json")
    InitializePaymentResponse initializeTransaction(InitializePaymentRequest request);

    @RequestLine("GET /transaction/verify/{reference}")
    VerifyPaymentResponse verifyTransaction(@Param("reference") String reference);

    @RequestLine("GET /transaction")
    TransactionResponse getTransaction();

    @RequestLine("GET/transaction/{id}")
    TransactionResponse getTransactionById(@Param("id") String id);


}

