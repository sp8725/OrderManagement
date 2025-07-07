package com.shubham.ordermanagement.assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shubham.ordermanagement.assessment.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
