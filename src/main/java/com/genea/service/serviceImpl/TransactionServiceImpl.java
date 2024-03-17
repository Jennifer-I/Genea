package com.genea.service.serviceImpl;

import com.genea.config.PayStackClient;
import com.genea.dto.request.InitializePaymentRequest;
import com.genea.dto.response.InitializePaymentResponse;
import com.genea.dto.response.TransactionResponse;
import com.genea.dto.response.VerifyPaymentResponse;
import com.genea.entity.Transaction;
import com.genea.entity.User;
import com.genea.enums.PaymentStatus;
import com.genea.repository.TransactionRepository;
import com.genea.repository.UserRepository;
import com.genea.service.TransactionService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@Transactional

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
    public InitializePaymentResponse initializePayment(InitializePaymentRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        request.setEmail(request.getEmail());
        try {
            InitializePaymentResponse response = paystackClient.initializeTransaction(request);

            if (response.getStatus().equals(true)) {
                Transaction transaction = new Transaction();
                transaction.setUser(user);
                transaction.setFirstName(user.getFirstName());
                transaction.setLastName(user.getLastName());
                transaction.setAmount(request.getAmount());
                transaction.setReference(response.getData().getReference());
                transaction.setCreatedAt(LocalDateTime.now().toString());
                transaction.setTransactionType("debit");
                transaction.setPaymentStatus(PaymentStatus.IN_PROGRESS);
                transaction.setDescription("Payment for " + response.getData().getReference());
                transactionRepository.save(transaction);
                return response;

            } else {
                log.error("An error occurred while initializing payment: {}",
                        response.getMessage());
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

    @Override
    public VerifyPaymentResponse verifyPayment(String reference) {
        try {
            VerifyPaymentResponse response = paystackClient.verifyTransaction(reference);
            if ("success".equalsIgnoreCase(response.getData().getStatus())) {
                Transaction transaction = transactionRepository.findByReference(reference).orElseThrow(() -> new RuntimeException("Transaction not found"));
                transaction.setPaymentStatus(PaymentStatus.SUCCESSFUL);
                transactionRepository.save(transaction);
                return response;
            } else {
                transactionRepository.findByReference(reference).ifPresent(transaction -> {
                    transaction.setPaymentStatus(PaymentStatus.FAILED);
                    transactionRepository.save(transaction);
                });
                log.error("An error occurred while verifying payment: {}",
                        response.getMessage());
            }
        } catch (FeignException e) {
            log.error("An error occurred while calling PayStack API: {}", e.contentUTF8());
        } catch (RuntimeException e) {
            log.error("Transaction not found: {}", e.getMessage());
        } catch (Exception e) {
            log.error("An error occurred: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public TransactionResponse getAllTransactions() {
        log.info("Retrieving all transactions from PayStack");
        TransactionResponse payStackTransactions = paystackClient.getTransaction();

        for (TransactionResponse.Data transaction : payStackTransactions.getTransactions()) {
            String userEmail = transaction.getCustomer().getEmail();
            User user = userRepository.findByEmail(userEmail).orElse(null);

            log.info("mapping user retrieved to transactions");
            if (user != null) {
                transaction.getCustomer().setFirstName(user.getFirstName());
                transaction.getCustomer().setLastName(user.getLastName());
                transaction.getCustomer().setPhone(user.getPhoneNumber());
            }
        }
        return payStackTransactions;
    }


    @Override
    public TransactionResponse getTransactionById(String id) {
        return paystackClient.getTransactionById(id);
    }


}
