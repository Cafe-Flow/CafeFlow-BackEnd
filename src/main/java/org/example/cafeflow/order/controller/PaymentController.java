package org.example.cafeflow.order.controller;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.order.dto.PaymentResponse;
import org.example.cafeflow.order.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/verify")
    public PaymentResponse verifyPayment(@RequestParam("impUid") String impUid) {
        return paymentService.verifyPayment(impUid);
    }
}
