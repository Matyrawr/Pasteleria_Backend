package com.pasteleria.backend.dto.transbank;

public class CommitResponse {
    private String status;
    private Double amount;
    private String buyOrder;
    private String cardNumber;
    private String authorizationCode;

    public CommitResponse() {}

    public CommitResponse(String status, Double amount, String buyOrder, String cardNumber, String authorizationCode) {
        this.status = status;
        this.amount = amount;
        this.buyOrder = buyOrder;
        this.cardNumber = cardNumber;
        this.authorizationCode = authorizationCode;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getBuyOrder() { return buyOrder; }
    public void setBuyOrder(String buyOrder) { this.buyOrder = buyOrder; }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getAuthorizationCode() { return authorizationCode; }
    public void setAuthorizationCode(String authorizationCode) { this.authorizationCode = authorizationCode; }
}
