package org.example.cafeflow.order.dto;

import lombok.Data;

@Data
public class PaymentResponse {
    private Response response;

    @Data
    public static class Response {
        private String imp_uid;
        private String merchant_uid;
        private String status;
        private double amount;
    }
}