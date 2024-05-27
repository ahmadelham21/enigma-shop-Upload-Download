package com.enigma.enigma_shop.specification;

import com.enigma.enigma_shop.dto.request.SearchProductRequest;
import com.enigma.enigma_shop.entity.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
	public static Specification<Product> getSpecification(SearchProductRequest request){
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if(request.getName() != null){
				predicates.add(
								criteriaBuilder.like(
												criteriaBuilder.lower(root.get("name")),
												"%" + request.getName().toLowerCase() + "%"
								)
				);
			}

			return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
		};
	}
}
