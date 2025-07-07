package com.shubham.ordermanagement.assessment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shubham.ordermanagement.assessment.entity.Customer;
import com.shubham.ordermanagement.assessment.repository.CustomerRepository;
import com.shubham.ordermanagement.assessment.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Customer createCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public Customer getCustomerById(Long id) {
		return customerRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Customer not found with id " + id));
	}

	@Override
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	@Override
	public Customer updateCustomer(Long id, Customer updatedCustomer) {
		Customer customer = getCustomerById(id);
		customer.setName(updatedCustomer.getName());
		customer.setEmail(updatedCustomer.getEmail());
		customer.setAddress(updatedCustomer.getAddress());
		return customerRepository.save(customer);
	}

	@Override
	public void deleteCustomer(Long id) {
		customerRepository.delete(getCustomerById(id));
	}
}
