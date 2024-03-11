package com.genea.config;

import com.genea.dto.request.InitializeTransactionRequest;
import com.genea.dto.response.InitializeTransactionResponse;
import com.genea.dto.response.VerifyTransactionResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface PayStackClient {

    @RequestLine("POST /transaction/initialize")
    @Headers("Content-Type: application/json")
    InitializeTransactionResponse initializeTransaction(InitializeTransactionRequest request);

    @RequestLine("GET /transaction/verify/{reference}")
    VerifyTransactionResponse verifyTransaction(@Param("reference") String reference);
}

