package com.shubham.ordermanagement.assessment.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.shubham.ordermanagement.assessment.entity.OrderItem;

public class OrderResponseDTO {
	private Long id;
	private LocalDateTime orderDate;
	private String status;
	private BigDecimal totalValue;
	private String customerName;
	private List<OrderItem> items;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = (items);
	}
}
