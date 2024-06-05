package org.example.cafeflow.order.dto;

import lombok.Data;

@Data
public class GetTokenResponse {
    private Response response;

    @Data
    public static class Response {
        private String access_token;
    }
}