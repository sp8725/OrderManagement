package com.shubham.ordermanagement.assessment.service;

import java.util.List;

import com.shubham.ordermanagement.assessment.dto.OrderResponseDTO;
import com.shubham.ordermanagement.assessment.entity.Order;

public interface OrderService {

	Order createOrder(Order order);

	List<OrderResponseDTO> getOrdersByCustomerId(Long customerId);

	Order updateOrderStatus(Long orderId, String status);

	Order getConfirmedOrderWithTotal(Long orderId);

	Order softDeleteOrder(Long orderId);
}