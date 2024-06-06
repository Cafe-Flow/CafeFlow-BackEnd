package org.example.cafeflow.order.service;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.Member.domain.Member;
import org.example.cafeflow.Member.repository.MemberRepository;
import org.example.cafeflow.order.domain.Orders;
import org.example.cafeflow.order.domain.OrderItem;
import org.example.cafeflow.order.domain.OrderStatus;
import org.example.cafeflow.order.dto.OrderDto;
import org.example.cafeflow.order.dto.PaymentResponse;
import org.example.cafeflow.order.repository.CafeMenuRepository;
import org.example.cafeflow.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final CafeMenuRepository cafeMenuRepository;
    private final PaymentService paymentService;

    public Orders createOrder(OrderDto orderDto, String impUid) {
        PaymentResponse paymentResponse = paymentService.verifyPayment(impUid);

        if (paymentResponse.getResponse().getStatus().equals("paid") &&
                paymentResponse.getResponse().getAmount() == orderDto.getTotalPrice()) {

            Member member = memberRepository.findById(orderDto.getMemberId())
                    .orElseThrow(() -> new IllegalArgumentException("Member not found"));

            List<OrderItem> orderItems = orderDto.getOrderItems().stream()
                    .map(itemDto -> {
                        var cafeMenu = cafeMenuRepository.findById(itemDto.getCafeMenuId())
                                .orElseThrow(() -> new IllegalArgumentException("Cafe menu not found"));
                        return OrderItem.builder()
                                .cafeMenu(cafeMenu)
                                .quantity(itemDto.getQuantity())
                                .price(itemDto.getPrice())
                                .build();
                    })
                    .collect(Collectors.toList());

            Orders orders = Orders.builder()
                    .member(member)
                    .orderItems(orderItems)
                    .orderDate(LocalDateTime.now())
                    .totalPrice(orderDto.getTotalPrice())
                    .status(OrderStatus.PENDING)
                    .build();

            return orderRepository.save(orders);
        } else {
            throw new IllegalArgumentException("Payment verification failed");
        }
    }

    public List<Orders> getOrdersByMemberId(Long memberId) {
        return orderRepository.findByMemberId(memberId);
    }

    public Orders updateOrderStatus(Long orderId, OrderStatus status) {
        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Orders not found"));
        orders.setStatus(status);
        return orderRepository.save(orders);
    }
}
