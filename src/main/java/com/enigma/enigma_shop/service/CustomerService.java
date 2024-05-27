package com.enigma.enigma_shop.service;

import com.enigma.enigma_shop.dto.request.SearchCustomerRequest;
import com.enigma.enigma_shop.dto.request.UpdateCustomerRequest;
import com.enigma.enigma_shop.dto.response.CustomerResponse;
import com.enigma.enigma_shop.entity.Customer;

import java.util.List;

public interface CustomerService {
	Customer create(Customer customer);
	Customer getById(String id);
	CustomerResponse getOneById(String id);
	List<CustomerResponse> getAll(SearchCustomerRequest searchCustomerRequest);
	CustomerResponse update(UpdateCustomerRequest customer);
	void deleteById(String id);

	void updateStatusById(String id, Boolean status);
}
