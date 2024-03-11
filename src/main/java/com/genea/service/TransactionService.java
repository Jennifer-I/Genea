package com.genea.service;


import com.genea.dto.request.InitializeTransactionRequest;
import com.genea.dto.response.InitializeTransactionResponse;
import org.springframework.stereotype.Service;



@Service

public interface TransactionService {


    InitializeTransactionResponse initializePayment(InitializeTransactionRequest request);
}
