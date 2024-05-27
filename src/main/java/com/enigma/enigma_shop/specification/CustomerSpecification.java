package com.enigma.enigma_shop.specification;

import com.enigma.enigma_shop.dto.request.SearchCustomerRequest;
import com.enigma.enigma_shop.entity.Customer;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class CustomerSpecification {
	public static Specification<Customer> getSpecification(SearchCustomerRequest request) {
//		return new Specification<Customer>() {
//			@Override
//			public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//				return null;
//			}
//		}
		/**
		 * 3 Object Criteria yg digunakan
		 * 	1. Criteria Builder -> untuk membangun query expression (<, >, like, <>, !-) dan memabngung (query-select, udpate, delete)
		 * 	2. Criteria Query (Query-Select, Update, Delete) ->  where, orderBy, having, groupBy, distint, join, from, order
		 * 	3. Root -> merepresntasikan dari entity (table)
		 * */
		return (root, query, criteriaBuilder) -> {
			// localhost:8080/customers?name=
			// ini kita anggap request param
//			String name = "budiono";
//			String phone = "0856238423";

			List<Predicate> predicates = new ArrayList<>();
			if (request.getName() != null) {
				Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + request.getName().toLowerCase() + "%");
				predicates.add(namePredicate);
			}

			if (request.getPhone() != null) {
				Predicate mobilePhoneNoPredicate = criteriaBuilder.equal(root.get("mobilePhoneNo"), request.getPhone());
				predicates.add(mobilePhoneNoPredicate);
			}

			if (request.getBirthDate() != null){
				// data string menjadi date sesuai dengan table
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//				Date parseDate = new Date();
//				try {
//					parseDate = sdf.parse(request.getBirthDate());
//				} catch (ParseException e) {
//					throw new RuntimeException(e);
//				}
				DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate parseDate = LocalDate.parse(request.getBirthDate(), dateTimeFormatter);
				Predicate birthDatePredicate = criteriaBuilder.equal(root.get("birthDate"), parseDate);
				predicates.add(birthDatePredicate);

			}

			if(request.getStatus() != null) {
				Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), request.getStatus());
				predicates.add(statusPredicate);
			}
			return query.where(criteriaBuilder.or(predicates.toArray(new Predicate[]{}))).getRestriction();
		};
	}
}
