package com.enigma.enigma_shop.controller;

import com.enigma.enigma_shop.constant.APIUrl;
import com.enigma.enigma_shop.dto.response.CommonResponse;
import com.enigma.enigma_shop.entity.Image;
import com.enigma.enigma_shop.entity.Product;
import com.enigma.enigma_shop.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class TestImageController {
	private final ImageService imageService;

	@GetMapping(path = "/api/v1/products/images/{imageId}")
	public ResponseEntity<?> downloadImage(
				@PathVariable(name = "imageId")	String id
	){
		Resource imageById = imageService.getById(id);

		//  "attachment; filename="filename.jpg""
		String headerValue = String.format("attachment; filename=%s",imageById.getFilename());

		// Content-Disposition: attachment; filename="filename.jpg"
		return ResponseEntity
						.status(HttpStatus.OK)
						.header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
						.body(imageById);

	}


	@PostMapping(path = "/api/v1/test-upload")
	public ResponseEntity<?> testUpload(
					@RequestPart(name = "image")MultipartFile multipartFile
					){
		Image image = imageService.create(multipartFile);
		return ResponseEntity.status(HttpStatus.CREATED).body(image);
	}

	@DeleteMapping(path = "/api/v1/test-delete/{id}")
	public ResponseEntity<?> deteleById(@PathVariable String id){
		imageService.deleteById(id);
		CommonResponse<Product> response = CommonResponse.<Product>builder()
				.statusCode(HttpStatus.OK.value())
				.message("OK, Succes Delete Product with id " + id)
				.build();
		return ResponseEntity.ok(response);
	}



}
