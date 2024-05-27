package com.enigma.enigma_shop.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
	private String id;
	private String name;
	private Long price;
	private Integer stock;
	private ImageResponse image;
}
