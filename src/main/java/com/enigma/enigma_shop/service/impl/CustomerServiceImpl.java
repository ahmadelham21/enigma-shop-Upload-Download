package com.enigma.enigma_shop.service.impl;

import com.enigma.enigma_shop.dto.request.SearchCustomerRequest;
import com.enigma.enigma_shop.dto.request.UpdateCustomerRequest;
import com.enigma.enigma_shop.dto.response.CustomerResponse;
import com.enigma.enigma_shop.entity.Customer;
import com.enigma.enigma_shop.entity.UserAccount;
import com.enigma.enigma_shop.repository.CustomerRepository;
import com.enigma.enigma_shop.service.CustomerService;
import com.enigma.enigma_shop.service.UserService;
import com.enigma.enigma_shop.specification.CustomerSpecification;
import com.enigma.enigma_shop.utils.ValidationUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
	private final CustomerRepository customerRepository;

	private final EntityManager entityManager;

	private final ValidationUtil validationUtil;

	private final UserService userService;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Customer create(Customer customer) {
		return customerRepository.saveAndFlush(customer);
	}

	@Transactional(readOnly = true)
	@Override
	public Customer getById(String id) {
		return findByIdOrThrowNotFound(id);
	}

	/**
	 * Menggunakan Specification/Criteria
	 * 1. Name
	 * 2. Mobile Phone
	 * 3. Birth Date
	 * 4. Status
	 *
	 * untuk operasinya and atau or
	 *1.  jika nama yg di cari ada, ya dapet namanya
	 *2.  cuman kalau namanya enggak sesuai, tapi mobile phonenya sesuai,
	 * yaudah sesuai dengan mobile phone nya
	 */


	public List<Customer> getAllAwal() {
		// localhost:8080/customers?name=
		// ini kita anggap request param

		String name = "budiono";
		String phone = "0856238423";

		// SELECT * FROM m_customer
		// kita ambil criteria builder
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		// ini baru melakukan query "SELECT" doang
		CriteriaQuery<Customer> query = criteriaBuilder.createQuery(Customer.class);

		// "FROM m_customer"
		Root<Customer> root = query.from(Customer.class);

		// SELECT * FROM m_customer
		query.select(root);

		// SELECT * FROM m_customer WHERE name LIKE %budiono%
		// SELECT * FROM m_customer WHERE mobile_phone_no EQUAL "0856238423"
		// defaultnya and
//		query.where(
//										// ini tablenya atau column lebih tepatnya
//						criteriaBuilder.like(root.get("name"), "%" + name + "%"),
//						criteriaBuilder.equal(root.get("mobilePhoneNo"),phone)
//		);
		// database    // request param
		// Budiono			// budiono
		// budiono			// budiono
		List<Predicate> predicates = new ArrayList<>();
		if(name != null){

			Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");

			predicates.add(namePredicate);
		}

		if(phone != null){
			Predicate mobilePhoneNoPredicate = criteriaBuilder.equal(root.get("mobilePhoneNo"), phone);
			predicates.add(mobilePhoneNoPredicate);
		}

		query.where(
			criteriaBuilder.or(
							predicates.toArray(new Predicate[]{})
			)
		);

//		return customerRepository.findAll();
//		entityManager.createQuery("SELECT * FROM m_customer");

		return entityManager.createQuery(query).getResultList();
	}

	@Transactional(readOnly = true)
	@Override
	public List<CustomerResponse> getAll(SearchCustomerRequest request) {
		Specification<Customer> customerSpecification = CustomerSpecification.getSpecification(request);
		if(request.getName() == null && request.getPhone() == null && request.getBirthDate() == null && request.getStatus() == null){
			return customerRepository.findAll().stream().map(this::parseCustomerToCustomerResponse).toList();
		}
		return customerRepository.findAll(customerSpecification).stream().map(this::parseCustomerToCustomerResponse).toList();
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public CustomerResponse update(UpdateCustomerRequest customerRequest) {
		validationUtil.validate(customerRequest);
		Customer currenCustomer = findByIdOrThrowNotFound(customerRequest.getId());

		UserAccount userAccount = userService.getByContext();

		// bisa update hanya diri sendiri untuk level ROLE_CUSTOMER
		if(!userAccount.getId().equals(currenCustomer.getUserAccount().getId())){
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,"user not found");
		}

		currenCustomer.setName(customerRequest.getName());
		currenCustomer.setMobilePhoneNo(customerRequest.getMobilePhoneNo());
		currenCustomer.setAddress(customerRequest.getAddress());
		currenCustomer.setBirthDate(Date.valueOf(String.valueOf(customerRequest.getBirthDate())));
		customerRepository.saveAndFlush(currenCustomer);

		return parseCustomerToCustomerResponse(currenCustomer);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteById(String id) {
		Customer customer = findByIdOrThrowNotFound(id);
		customerRepository.delete(customer);
	}

	private Customer findByIdOrThrowNotFound(String id) {
		// artinya, kita findById, kalau enggak ada dilempar atau di Throw,
		// jadi kan sebenernya di bungkus sama optional dan kita pakai.get maka datanya menjadi Customer kayak kemaren. tapi dengan .orElseThrow kita juga gunakan .get ketika ada datanya dan otomatis di lempat throw ketika data null
		return customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found"));
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateStatusById(String id, Boolean status) {
		findByIdOrThrowNotFound(id);
		customerRepository.updateStatus(id,status);
	}

	@Override
	public CustomerResponse getOneById(String id) {
		Customer customerById = findByIdOrThrowNotFound(id);
		return parseCustomerToCustomerResponse(customerById);
	}

	private CustomerResponse parseCustomerToCustomerResponse(Customer customer){

		String userId;
		if(customer.getUserAccount() == null){
			userId = null;
		} else {
			userId = customer.getUserAccount().getId();
		}
		return CustomerResponse.builder()
						.id(customer.getId())
						.name(customer.getName())
						.mobilePhoneNo(customer.getMobilePhoneNo())
						.address(customer.getAddress())
						.status(customer.getStatus())
						.userAccountId(userId)
						.build();
	}
}
