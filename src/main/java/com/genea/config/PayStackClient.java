package com.genea.config;

import com.genea.dto.request.InitializePaymentRequest;
import com.genea.dto.response.InitializePaymentResponse;
import com.genea.dto.response.TransactionResponse;
import com.genea.dto.response.VerifyPaymentResponse;
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

