package com.pasteleria.backend.dto.transbank;

public class StatusResponse {
    private String status;
    private Double amount;
    private String buyOrder;

    public StatusResponse() {}

    public StatusResponse(String status, Double amount, String buyOrder) {
        this.status = status;
        this.amount = amount;
        this.buyOrder = buyOrder;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getBuyOrder() { return buyOrder; }
    public void setBuyOrder(String buyOrder) { this.buyOrder = buyOrder; }
}
