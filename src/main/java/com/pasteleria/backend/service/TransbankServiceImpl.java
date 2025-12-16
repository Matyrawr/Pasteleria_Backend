package com.pasteleria.backend.service;

import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCreateResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;
import com.pasteleria.backend.dto.transbank.CreateTransactionResponse;
import com.pasteleria.backend.dto.transbank.CommitResponse;
import com.pasteleria.backend.dto.transbank.StatusResponse;

@Service
public class TransbankServiceImpl implements TransbankService {

    private final WebpayPlus.Transaction tx;

    public TransbankServiceImpl() {
        // Configure Transbank SDK for integration environment
        this.tx = new WebpayPlus.Transaction(new WebpayOptions(IntegrationCommerceCodes.WEBPAY_PLUS, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
    }

    @Override
    public CreateTransactionResponse createTransaction(double amount) throws Exception {
        // buyOrder max length 26 per Transbank spec
        String buyOrder = String.valueOf(System.currentTimeMillis()); // 13 digits
        // Append a short random suffix to avoid collisions, keep <= 26
        String randomSuffix = String.valueOf(Math.abs(UUID.randomUUID().getMostSignificantBits())).substring(0, 10);
        buyOrder = (buyOrder + randomSuffix).substring(0, Math.min(26, buyOrder.length() + randomSuffix.length()));

        // sessionId can be longer; use UUID trimmed
        final String sessionId = ("S" + UUID.randomUUID().toString().replace("-", "")).substring(0, 20);
        // Use amount provided by cart total (must be numeric CLP)
        final String returnUrl = "http://localhost:5173/pago/resultado"; // frontend landing page

        final WebpayPlusTransactionCreateResponse response = this.tx.create(buyOrder, sessionId, amount, returnUrl);
        return new CreateTransactionResponse(response.getUrl(), response.getToken());
    }

    @Override
    public CommitResponse commitTransaction(String token) throws Exception {
        var commit = this.tx.commit(token);
        String cardNumber = commit.getCardDetail() != null ? commit.getCardDetail().getCardNumber() : null;
        return new CommitResponse(commit.getStatus(), commit.getAmount(), commit.getBuyOrder(), cardNumber, commit.getAuthorizationCode());
    }

    @Override
    public StatusResponse getTransactionStatus(String token) throws Exception {
        var status = this.tx.status(token);
        return new StatusResponse(status.getStatus(), status.getAmount(), status.getBuyOrder());
    }
}