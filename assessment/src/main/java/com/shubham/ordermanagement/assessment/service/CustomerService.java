package com.shubham.ordermanagement.assessment.service;

import java.util.List;

import com.shubham.ordermanagement.assessment.entity.Customer;

public interface CustomerService {
	Customer createCustomer(Customer customer);

	Customer getCustomerById(Long id);

	List<Customer> getAllCustomers();

	Customer updateCustomer(Long id, Customer customer);

	void deleteCustomer(Long id);
}
