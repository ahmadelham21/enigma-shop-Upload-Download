package com.enigma.enigma_shop.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageResponse {
	// url = "/api/v1/products/images/{id}
	private String url;
	private String name;
}
