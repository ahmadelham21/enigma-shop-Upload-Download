package com.enigma.enigma_shop.service.impl;

import com.enigma.enigma_shop.constant.APIUrl;
import com.enigma.enigma_shop.dto.request.NewProductRequest;
import com.enigma.enigma_shop.dto.request.SearchProductRequest;
import com.enigma.enigma_shop.dto.request.UpdateProductRequest;
import com.enigma.enigma_shop.dto.response.ImageResponse;
import com.enigma.enigma_shop.dto.response.ProductResponse;
import com.enigma.enigma_shop.entity.Image;
import com.enigma.enigma_shop.entity.Product;
import com.enigma.enigma_shop.repository.ProductRepository;
import com.enigma.enigma_shop.service.ImageService;
import com.enigma.enigma_shop.service.ProductService;
import com.enigma.enigma_shop.specification.ProductSpecification;
import com.enigma.enigma_shop.utils.ValidationUtil;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

//	@Autowired
//	public ProductServiceImpl(ProductRepository productRepository) {
//		this.productRepository = productRepository;
//	}
	private final ValidationUtil validationUtil;

	private final ImageService imageService;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public ProductResponse create(NewProductRequest productRequest) {
		validationUtil.validate(productRequest); // wajib di lakukan di awal method, sebelum kalian buat logic-logic aneh

		if (productRequest.getImage().isEmpty()){
			throw new ConstraintViolationException("image is required", null);
		}

		Image image = imageService.create(productRequest.getImage());

		Product newProduct = Product.builder()
						.name(productRequest.getName())
						.price(productRequest.getPrice())
						.stock(productRequest.getStock())
						.image(image)
						.build();
		productRepository.saveAndFlush(newProduct);
		return parseProductToProductResponse(newProduct);
		// saveAndFlush, jadi ketika product di save, dia akan otomatis mengembalikan id yg berhasil ke save
	}

	@Transactional(readOnly = true)
	@Override
	public Product getById(String id) {
		Optional<Product> optionalProduct = productRepository.findById(id);
		if (optionalProduct.isEmpty()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found");
		}
		return optionalProduct.get();
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Product> getAll(SearchProductRequest productRequest) {
//		if (name != null){
//      return productRepository.findAllByNameLike("%" + name + "%");
//    }
		if(productRequest.getPage() <= 0) {
			productRequest.setPage(1);
		}

		String validSortBy;
		if("name".equalsIgnoreCase(productRequest.getSortBy()) || "price".equalsIgnoreCase(productRequest.getSortBy()) || "stock".equalsIgnoreCase(productRequest.getSortBy())){
			validSortBy = productRequest.getSortBy();
		} else {
			validSortBy = "name";
		}

		Sort sort = Sort.by(Sort.Direction.fromString(productRequest.getDirection()), /**productRequest.getSortBy()*/ validSortBy);

		Pageable pageable = PageRequest.of((productRequest.getPage() - 1), productRequest.getSize(), sort); // rumus pagination


		Specification<Product> specification = ProductSpecification.getSpecification(productRequest);
		return productRepository.findAll(specification, pageable);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Product update(UpdateProductRequest productRequest) {
		/**
		 * ubah gambar, jika ada gambar yg dikirimkan. caranya hapus gambar yg lama dan ganti gambar baru
		 * jika tidak ada gambar, maka ubah detail productnya saja. gambarnya jangan di apa-apakan.
		 * */
//		productRepository.findById(product.getId());
		Product byId = getById(productRequest.getId());
		String imageToDelete = byId.getImage().getId();
		Product.ProductBuilder productBuilder = Product.builder();
		productBuilder.id(productRequest.getId());
		productBuilder.name(productRequest.getName());
		productBuilder.stock(productRequest.getStock());
		productBuilder.price(productRequest.getPrice());

		if (productRequest.getImage() != null){
			Image image = imageService.create(productRequest.getImage());
			imageService.deleteById(imageToDelete);
			productBuilder.image(image);
		}else {
			productBuilder.image(byId.getImage());
		}
		return productRepository.saveAndFlush(productBuilder.build());

	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteById(String id) {
		Product currentProduct = getById(id);
		productRepository.delete(currentProduct);

	}

	private ProductResponse parseProductToProductResponse(Product product){

		String imageId;
		String name;
		if(product.getImage() == null){
			imageId = null;
			name = null;
		} else {
			imageId = product.getImage().getId();
			name = product.getImage().getName();
		}

		return ProductResponse.builder()
						.id(product.getId())
						.name(product.getName())
						.price(product.getPrice())
						.stock(product.getStock())
						.image(ImageResponse.builder()
										.url(APIUrl.PRODUCT_IMAGE_DOWNLOAD_API + imageId)
										.name(name)
										.build())
						.build();
	}
}
