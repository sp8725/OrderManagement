package com.shubham.ordermanagement.assessment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shubham.ordermanagement.assessment.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByCustomerIdAndDeletedFalse(Long customerId);

	Order findByIdAndDeletedFalse(Long id);
}