package com.pasteleria.backend.service;

import com.pasteleria.backend.dto.transbank.CreateTransactionResponse;
import com.pasteleria.backend.dto.transbank.CommitResponse;
import com.pasteleria.backend.dto.transbank.StatusResponse;

public interface TransbankService {
    CreateTransactionResponse createTransaction(double amount) throws Exception;
    CommitResponse commitTransaction(String token) throws Exception;
    StatusResponse getTransactionStatus(String token) throws Exception;
}
