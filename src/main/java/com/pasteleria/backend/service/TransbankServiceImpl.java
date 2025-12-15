package com.pasteleria.backend.service;

import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCreateResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransbankServiceImpl implements TransbankService {

    private final WebpayPlus.Transaction tx;

    public TransbankServiceImpl() {
        // Configure Transbank SDK for integration environment
        this.tx = new WebpayPlus.Transaction(new WebpayOptions(IntegrationCommerceCodes.WEBPAY_PLUS, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
    }

    @Override
    public String createTransaction() throws Exception {
        final String buyOrder = "buyOrder-" + UUID.randomUUID().toString();
        final String sessionId = "sessionId-" + UUID.randomUUID().toString();
        final double amount = 1000;
        final String returnUrl = "http://localhost:8080/api/transbank/commit"; // Placeholder

        final WebpayPlusTransactionCreateResponse response = this.tx.create(buyOrder, sessionId, amount, returnUrl);

        // For a real application, you would redirect the user to response.getUrl()
        // and provide the token. For this example, we return a string with the details.
        return "URL: " + response.getUrl() + ", Token: " + response.getToken();
    }
}