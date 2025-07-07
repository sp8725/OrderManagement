package com.shubham.ordermanagement.assessment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shubham.ordermanagement.assessment.entity.Customer;
import com.shubham.ordermanagement.assessment.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
		return ResponseEntity.ok(customerService.createCustomer(customer));
	}

	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomers() {
		return ResponseEntity.ok(customerService.getAllCustomers());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id) {
		return ResponseEntity.ok(customerService.getCustomerById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer) {
		return ResponseEntity.ok(customerService.updateCustomer(id, customer));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> deleteCustomer(@PathVariable("id") Long id) {
		customerService.deleteCustomer(id);
		Map<String, String> response = new HashMap<>();
		response.put("message", "Customer deleted successfully");
		return ResponseEntity.ok(response);
	}

}
