package com.enigma.enigma_shop.service;


import com.enigma.enigma_shop.entity.Image;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
	Image create(MultipartFile multipartFile);

	Image update(MultipartFile multipartFile);

	Resource getById(String id);

	void deleteById(String id);
}
