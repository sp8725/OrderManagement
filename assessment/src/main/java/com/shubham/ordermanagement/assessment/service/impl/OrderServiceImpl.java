package com.shubham.ordermanagement.assessment.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shubham.ordermanagement.assessment.dto.OrderResponseDTO;
import com.shubham.ordermanagement.assessment.entity.Customer;
import com.shubham.ordermanagement.assessment.entity.Order;
import com.shubham.ordermanagement.assessment.entity.OrderItem;
import com.shubham.ordermanagement.assessment.entity.OrderStatus;
import com.shubham.ordermanagement.assessment.repository.CustomerRepository;
import com.shubham.ordermanagement.assessment.repository.OrderRepository;
import com.shubham.ordermanagement.assessment.service.OrderService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Order createOrder(Order order) {
		order.setStatus(OrderStatus.PENDING);
		order.setDeleted(false);
		if (!order.getItems().isEmpty()) {
			for (OrderItem item : order.getItems()) {
				item.setOrder(order);
			}
		}
		if (order.getCustomer() != null && order.getCustomer().getId() != null) {
			Customer fullCustomer = customerRepository.findById(order.getCustomer().getId()).orElseThrow(
					() -> new RuntimeException("Customer not found with ID: " + order.getCustomer().getId()));
			order.setCustomer(fullCustomer);
		}
		Order savedOrder = orderRepository.save(order);
		finalizeOrderAsync(savedOrder.getId());
		return savedOrder;
	}

	@Async
	public CompletableFuture<Void> finalizeOrderAsync(Long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new EntityNotFoundException("Order not found"));
		BigDecimal total = order.getItems().stream()
				.map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		order.setTotalValue(total);
		order.setStatus(OrderStatus.CONFIRMED);
		orderRepository.save(order);
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public List<OrderResponseDTO> getOrdersByCustomerId(Long customerId) {
		List<Order> orders = orderRepository.findByCustomerIdAndDeletedFalse(customerId);
		List<OrderResponseDTO> responseList = new ArrayList<>();

		for (Order order : orders) {
			OrderResponseDTO dto = new OrderResponseDTO();
			dto.setId(order.getId());
			dto.setOrderDate(order.getOrderDate());
			dto.setStatus(order.getStatus().toString());
			dto.setTotalValue(order.getTotalValue());
			dto.setCustomerName(order.getCustomer() != null ? order.getCustomer().getName() : null);
			dto.setItems(order.getItems());
			if (!order.getItems().isEmpty()) {
				List<OrderItem> itemDTOs = new ArrayList<>();
				for (OrderItem item : order.getItems()) {
					OrderItem itemDTO = new OrderItem();
					itemDTO.setId(item.getId());
					itemDTO.setItemName(item.getItemName());
					itemDTO.setPrice(item.getPrice());
					itemDTO.setQuantity(item.getQuantity());
					itemDTOs.add(itemDTO);
				}
				dto.setItems(itemDTOs);
			}

			responseList.add(dto);
		}

		return responseList;
	}

	@Override
	public Order updateOrderStatus(Long orderId, String status) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new EntityNotFoundException("Order not found"));
		if (order.getCustomer() != null && order.getCustomer().getId() != null) {
			Customer fullCustomer = customerRepository.findById(order.getCustomer().getId()).orElseThrow(
					() -> new RuntimeException("Customer not found with ID: " + order.getCustomer().getId()));
			order.setCustomer(fullCustomer);
		}
		order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
		return orderRepository.save(order);
	}

	@Override
	public Order getConfirmedOrderWithTotal(Long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new EntityNotFoundException("Order not found"));
		if (order.getCustomer() != null && order.getCustomer().getId() != null) {
			Customer fullCustomer = customerRepository.findById(order.getCustomer().getId()).orElseThrow(
					() -> new RuntimeException("Customer not found with ID: " + order.getCustomer().getId()));
			order.setCustomer(fullCustomer);
		}
		if (order.getStatus() != OrderStatus.CONFIRMED) {
			throw new IllegalStateException("Order is not yet confirmed");
		}
		return order;
	}

	@Override
	@Transactional
	public Order softDeleteOrder(Long orderId) {
		return orderRepository.findById(orderId).map(order -> {
			order.setDeleted(true);
			if (order.getCustomer() != null && order.getCustomer().getId() != null) {
				Customer fullCustomer = customerRepository.findById(order.getCustomer().getId()).orElseThrow(
						() -> new RuntimeException("Customer not found with ID: " + order.getCustomer().getId()));
				order.setCustomer(fullCustomer);
			}
			return orderRepository.save(order);
		}).orElseThrow(() -> new RuntimeException("Order not found"));
	}
}
