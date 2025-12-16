package com.pasteleria.backend.dto.promo;

public class PromoValidationResponse {
    private String code;
    private boolean valid;
    private int percent; // porcentaje entero, ej: 10 = 10%

    public PromoValidationResponse() {}

    public PromoValidationResponse(String code, boolean valid, int percent) {
        this.code = code;
        this.valid = valid;
        this.percent = percent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
