package com.enigma.enigma_shop.service;

import com.enigma.enigma_shop.dto.request.NewProductRequest;
import com.enigma.enigma_shop.dto.request.SearchProductRequest;
import com.enigma.enigma_shop.dto.request.UpdateProductRequest;
import com.enigma.enigma_shop.dto.response.ProductResponse;
import com.enigma.enigma_shop.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
	ProductResponse create(NewProductRequest productRequest);
	Product getById(String id);
	Page<Product> getAll(SearchProductRequest productRequest);
	Product update(UpdateProductRequest productRequest);
	void deleteById(String id);
}
