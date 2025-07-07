package com.shubham.ordermanagement.assessment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shubham.ordermanagement.assessment.dto.OrderResponseDTO;
import com.shubham.ordermanagement.assessment.entity.Order;
import com.shubham.ordermanagement.assessment.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping
	public ResponseEntity<Order> createOrder(@RequestBody Order order) {
		Order created = orderService.createOrder(order);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@GetMapping("/customer/{customerId}")
	public ResponseEntity<List<OrderResponseDTO>> getOrdersByCustomer(@PathVariable("customerId") Long customerId) {
		return ResponseEntity.ok(orderService.getOrdersByCustomerId(customerId));
	}

	@PutMapping("/{orderId}/status")
	public ResponseEntity<Order> updateOrderStatus(@PathVariable("orderId") Long orderId,
			@RequestParam("status") String status) {
		return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
	}

	@GetMapping("/{orderId}/total")
	public ResponseEntity<Order> getConfirmedOrderWithTotal(@PathVariable("orderId") Long orderId) {
		return ResponseEntity.ok(orderService.getConfirmedOrderWithTotal(orderId));
	}

	@DeleteMapping("/{orderId}")
	public ResponseEntity<Order> softDeleteOrder(@PathVariable("orderId") Long id) {
		Order deletedOrder = orderService.softDeleteOrder(id);
		return ResponseEntity.ok(deletedOrder);
	}

}
