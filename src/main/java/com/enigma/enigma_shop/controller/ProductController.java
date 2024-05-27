package com.enigma.enigma_shop.controller;

import com.enigma.enigma_shop.constant.APIUrl;
import com.enigma.enigma_shop.dto.request.NewProductRequest;
import com.enigma.enigma_shop.dto.request.SearchProductRequest;
import com.enigma.enigma_shop.dto.request.UpdateProductRequest;
import com.enigma.enigma_shop.dto.response.CommonResponse;
import com.enigma.enigma_shop.dto.response.PagingResponse;
import com.enigma.enigma_shop.dto.response.ProductResponse;
import com.enigma.enigma_shop.entity.Product;
import com.enigma.enigma_shop.service.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.PRODUCT_API)
public class ProductController {
	private final ProductService productService;

	private final ObjectMapper objectMapper;

//	@PostMapping
//	public ResponseEntity<CommonResponse<Product>> createNewProduct(@RequestBody NewProductRequest productRequest) {
//		Product newProduct = productService.create(productRequest);
//		CommonResponse<Product> response = CommonResponse.<Product>builder()
//						.statusCode(HttpStatus.CREATED.value())
//						.message("successfully create new product")
//						.data(newProduct)
//						.build();
//
//		return ResponseEntity
//						.status(HttpStatus.CREATED)
//						.body(response);
//	}
	@PostMapping(
					consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
					produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<CommonResponse<?>> createNewProductWithImage(
					@RequestPart(name = "product") String jsonProductRequest,
					@RequestPart(name = "image") MultipartFile productImage
					){

		CommonResponse.CommonResponseBuilder<ProductResponse> responseBuilder = CommonResponse.builder();

		try {
			NewProductRequest productRequest = objectMapper.readValue(jsonProductRequest, new TypeReference<>() {
			});
			productRequest.setImage(productImage);
			ProductResponse newProduct = productService.create(productRequest);

			responseBuilder.statusCode(HttpStatus.CREATED.value());
			responseBuilder.message("successfully save data");
			responseBuilder.data(newProduct);

			return ResponseEntity.status(HttpStatus.CREATED).body(responseBuilder.build());

		}catch (Exception e){
			responseBuilder.message("internal server error");
			responseBuilder.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBuilder.build());
		}

	}

	@GetMapping(path = APIUrl.PATH_VAR_ID)
	public ResponseEntity<CommonResponse<Product>> getById(@PathVariable String id){
		Product product = productService.getById(id);
		CommonResponse<Product> response = CommonResponse.<Product>builder()
						.statusCode(HttpStatus.OK.value())
						.message("successfully fetch data")
						.data(product)
						.build();
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<CommonResponse<List<Product>>> getAllProduct(
					@RequestParam(name = "page", defaultValue = "1") Integer page,
					@RequestParam(name = "size", defaultValue = "10") Integer size,
					@RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
					@RequestParam(name = "direction", defaultValue = "asc") String direction,
					@RequestParam(name = "name", required = false) String name
	) {


		SearchProductRequest request = SearchProductRequest.builder()
						.page(page)
						.size(size)
						.sortBy(sortBy)
						.direction(direction)
						.name(name)
						.build();
		Page<Product> productAll = productService.getAll(request);

		PagingResponse pagingResponse = PagingResponse.builder()
						.totalPages(productAll.getTotalPages())
						.totalElements(productAll.getTotalElements())
						.page(productAll.getPageable().getPageNumber() + 1)
						.size(productAll.getPageable().getPageSize())
						.hasNext(productAll.hasNext())
						.hasPrevious(productAll.hasPrevious())
						.build();

		CommonResponse<List<Product>> response = CommonResponse.<List<Product>>builder()
						.statusCode(HttpStatus.OK.value())
						.message("success get all product")
						.data(productAll.getContent())
						.paging(pagingResponse)
						.build();

		return ResponseEntity.ok(response);
	}

	@PutMapping(
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<CommonResponse<?>> updateProduct(
			@RequestPart(name = "product") String jsonProductRequest,
			@RequestPart(name = "image",required = false) MultipartFile productImage
	) {
		CommonResponse.CommonResponseBuilder<Product> responseBuilder = CommonResponse.builder();
		try {
			UpdateProductRequest productRequest = objectMapper.readValue(jsonProductRequest, new TypeReference<>() {
			});

			productRequest.setImage(productImage);

			Product updateProduct = productService.update(productRequest);

			responseBuilder.statusCode(HttpStatus.CREATED.value());
			responseBuilder.message("successfully save data");
			responseBuilder.data(updateProduct);

			return ResponseEntity.status(HttpStatus.CREATED).body(responseBuilder.build());

		}catch (Exception e){
			responseBuilder.message(e.getMessage());
			responseBuilder.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBuilder.build());
		}

	}

	@DeleteMapping(path = APIUrl.PATH_VAR_ID)
	public ResponseEntity<CommonResponse<?>> deteleById(@PathVariable String id){
		productService.deleteById(id);
		CommonResponse<Product> response = CommonResponse.<Product>builder()
						.statusCode(HttpStatus.OK.value())
						.message("OK, Succes Delete Product with id " + id)
						.build();
		return ResponseEntity.ok(response);
	}
}
