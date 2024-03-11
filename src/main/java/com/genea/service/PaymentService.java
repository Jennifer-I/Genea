package com.genea.service;


import com.genea.dto.request.InitializePaymentRequest;
import com.genea.dto.response.InitializePaymentResponse;
import com.genea.dto.response.VerifyPaymentResponse;
import org.springframework.stereotype.Service;



@Service

public interface PaymentService {


    InitializePaymentResponse initializePayment(InitializePaymentRequest request);

    VerifyPaymentResponse verifyPayment(String reference);
}
