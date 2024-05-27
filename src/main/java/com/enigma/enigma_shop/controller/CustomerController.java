package com.enigma.enigma_shop.controller;

import com.enigma.enigma_shop.constant.APIUrl;
import com.enigma.enigma_shop.constant.ResponseMessage;
import com.enigma.enigma_shop.dto.request.SearchCustomerRequest;
import com.enigma.enigma_shop.dto.request.UpdateCustomerRequest;
import com.enigma.enigma_shop.dto.response.CommonResponse;
import com.enigma.enigma_shop.dto.response.CustomerResponse;
import com.enigma.enigma_shop.entity.Customer;
import com.enigma.enigma_shop.service.CustomerService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.CUSTOMER_API)
public class CustomerController {
	private final CustomerService customerService;
//	@PostMapping
//	public ResponseEntity<CommonResponse<Customer>> createNewCustomer(@RequestBody Customer product) {
//		Customer newCustomer = customerService.create(product);
//		CommonResponse<Customer> response = CommonResponse.<Customer>builder()
//						.statusCode(HttpStatus.CREATED.value())
//						.message(ResponseMessage.SUCCESS_SAVE_DATA)
//						.data(newCustomer)
//						.build();
//		return ResponseEntity
//						.status(HttpStatus.CREATED)
//						.body(response);
//	}

	@GetMapping(path = APIUrl.PATH_VAR_ID)
	public ResponseEntity<CommonResponse<Customer>> getCustomerById(@PathVariable String id) {
		Customer customerById = customerService.getById(id);
		CommonResponse<Customer> response = CommonResponse.<Customer>builder()
						.statusCode(HttpStatus.OK.value())
						.message(ResponseMessage.SUCCESS_GET_DATA)
						.data(customerById)
						.build();
		return ResponseEntity.ok(response);
	}

	// hasAnyRole() -> multi role
	// hasRole() -> single role
	@PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
	@GetMapping
	public ResponseEntity<CommonResponse<List<CustomerResponse>>> getAllCustomer(
					@RequestParam(name = "name", required = false ) String name,
					@RequestParam(name = "mobilePhoneNo", required = false) String phone,
					@RequestParam(name = "birthDate", required = false ) @JsonFormat(pattern = "yyyy-MM-dd") String birthDate,
					@RequestParam(name = "status", required = false) Boolean status

	) {
//		Let object ={name,phone,date,status}
//		SearchCustomerRequest searchCustomerRequest = new SearchCustomerRequest(name, phone, null, status);
		SearchCustomerRequest searchCustomerRequest = SearchCustomerRequest.builder()
						.name(name)
						.phone(phone)
						.birthDate(birthDate)
						.status(status)
						.build(); // seperti keyword new

		List<CustomerResponse> customerList = customerService.getAll(searchCustomerRequest);
		CommonResponse<List<CustomerResponse>> response = CommonResponse.<List<CustomerResponse>>builder()
						.statusCode(HttpStatus.OK.value())
						.message(ResponseMessage.SUCCESS_GET_DATA)
						.data(customerList)
						.build();
		return ResponseEntity.ok(response);
	}

	@PutMapping
	public ResponseEntity<CommonResponse<CustomerResponse>> updateCustomer(@RequestBody UpdateCustomerRequest customer) {
		CustomerResponse updateCustomer = customerService.update(customer);
		CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
						.statusCode(HttpStatus.OK.value())
						.message(ResponseMessage.SUCCESS_UPDATE_DATA)
						.data(updateCustomer)
						.build();
		return ResponseEntity.ok(response);
	}

	@DeleteMapping(path = APIUrl.PATH_VAR_ID)
	public ResponseEntity<CommonResponse<String>>  deleteById(@PathVariable String id) {
		customerService.deleteById(id);
		CommonResponse<String> response = CommonResponse.<String>builder()
						.statusCode(HttpStatus.OK.value())
						.message(ResponseMessage.SUCCESS_DELETE_DATA)
						.build();
		return ResponseEntity.ok(response);
	}

	@PutMapping(path = APIUrl.PATH_VAR_ID)
	public ResponseEntity<CommonResponse<String>> updateStatus(
					@PathVariable String id,
					@RequestParam(name = "status") Boolean status
	){
		customerService.updateStatusById(id, status);
		CommonResponse<String> response = CommonResponse.<String>builder()
						.statusCode(HttpStatus.OK.value())
						.message(ResponseMessage.SUCCESS_UPDATE_DATA)
						.build();
		return ResponseEntity.ok(response);
	}
}
