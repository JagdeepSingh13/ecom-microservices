package com.ecom.order.service;

import com.ecom.order.dto.OrderItemDto;
import com.ecom.order.dto.OrderResponse;
import com.ecom.order.model.*;
import com.ecom.order.repositories.OrderRepository;
import com.ecom.order.model.Order;
import com.ecom.order.model.OrderItem;
import com.ecom.order.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartService cartService;
//    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public Optional<OrderResponse> createOrder(String userId) {
//        validate for cart items
        List<CartItem> cartItems = cartService.getCart(userId);
        if (cartItems.isEmpty()) {
            return Optional.empty();
        }

//        validate for users
//        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
//        if (userOpt.isEmpty()) {
//            return Optional.empty();
//        }
//        User user = userOpt.get();

//        calculate total price (price in cartItem already p.price*quantity)
        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

//        create order
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);

        List<OrderItem> orderItems = cartItems.stream()
                        .map(item -> new OrderItem(
                                null,
                                item.getProductId(),
                                item.getQuantity(),
                                item.getPrice(),
                                order
                        ))
                        .toList();

        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);

//        clear the cart
        cartService.clearCart(userId);

        return Optional.of(mapToOrderResponse(savedOrder));
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getItems().stream()
                        .map(item -> new OrderItemDto(
                                item.getId(),
                                item.getProductId(),
                                item.getQuantity(),
                                item.getPrice(),
                                item.getPrice()//.multiply(new BigDecimal(item.getQuantity()))
                        ))
                        .toList(),
                order.getCreatedAt()
        );
    }

}
