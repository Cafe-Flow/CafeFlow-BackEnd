package org.example.cafeflow.order.service;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.example.cafeflow.order.dto.GetTokenRequest;
import org.example.cafeflow.order.dto.GetTokenResponse;
import org.example.cafeflow.order.dto.PaymentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Value("${iamport.api-key}")
    private String impKey;

    @Value("${iamport.api-secret}")
    private String impSecret;

    private final RestTemplate restTemplate;
    private final Gson gson;

    // 아임포트 액세스 토큰 발급
    public String getImportToken() {
        String url = "https://api.iamport.kr/users/getToken";
        GetTokenRequest getTokenRequest = new GetTokenRequest(impKey, impSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(gson.toJson(getTokenRequest), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        GetTokenResponse tokenResponse = gson.fromJson(response.getBody(), GetTokenResponse.class);

        if (tokenResponse.getResponse() == null) {
            throw new RuntimeException("Failed to get access token from Iamport");
        }

        return tokenResponse.getResponse().getAccess_token();
    }

    // 결제 검증
    public PaymentResponse verifyPayment(String impUid) {
        String token = getImportToken();
        String url = "https://api.iamport.kr/payments/" + impUid;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return gson.fromJson(response.getBody(), PaymentResponse.class);
    }
}
