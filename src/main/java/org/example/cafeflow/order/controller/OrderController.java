package org.example.cafeflow.order.controller;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.order.domain.Orders;
import org.example.cafeflow.order.domain.OrderStatus;
import org.example.cafeflow.order.dto.OrderDto;
import org.example.cafeflow.order.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public Orders createOrder(@RequestBody OrderDto orderDto, @RequestParam("impUid") String impUid) {
        return orderService.createOrder(orderDto, impUid);
    }

    @GetMapping("/member/{memberId}")
    public List<Orders> getOrdersByMemberId(@PathVariable("memberId") Long memberId) {
        return orderService.getOrdersByMemberId(memberId);
    }

    @PatchMapping("/{orderId}/status")
    public Orders updateOrderStatus(@PathVariable("orderId") Long orderId, @RequestParam("status") OrderStatus status) {
        return orderService.updateOrderStatus(orderId, status);
    }
}
