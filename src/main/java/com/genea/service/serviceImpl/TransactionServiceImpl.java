package com.genea.service.serviceImpl;

import com.genea.config.PayStackClient;
import com.genea.dto.request.InitializeTransactionRequest;
import com.genea.dto.response.InitializeTransactionResponse;
import com.genea.entity.Transaction;
import com.genea.entity.User;
import com.genea.repository.TransactionRepository;
import com.genea.repository.UserRepository;
import com.genea.service.TransactionService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j


public class TransactionServiceImpl implements TransactionService {
    private final PayStackClient paystackClient;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(PayStackClient paystackClient, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.paystackClient = paystackClient;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }


    @Override
    public InitializeTransactionResponse initializePayment(InitializeTransactionRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        request.setEmail(request.getEmail());
        try {
            InitializeTransactionResponse response = paystackClient.initializeTransaction(request);

            if (response.getStatus().equalsIgnoreCase("true")) {
                Transaction transaction = new Transaction();
                transaction.setUser(user);
                transaction.setAmount(request.getAmount());
                transaction.setReference(response.getData().getReference());
                transaction.setCreatedAt(LocalDateTime.now().toString());
                transaction.setTransactionType("debit");
                transaction.setDescription("Payment for " + request.getReference());
                transactionRepository.save(transaction);
                return response;

            } else {
                log.error("An error occurred while initializing payment: {}",
                        response != null ? response.getMessage() : "No response received");
            }
        } catch (FeignException e) {

            log.error("An error occurred while calling Paystack API: {}", e.contentUTF8());
        } catch (RuntimeException e) {

            log.error("User not found: {}", e.getMessage());
        } catch (Exception e) {

            log.error("An error occurred: {}", e.getMessage());
        }

        return null;
    }


}
