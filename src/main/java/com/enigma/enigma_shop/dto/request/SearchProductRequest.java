package com.enigma.enigma_shop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchProductRequest {
	private Integer page;
	private Integer size;

	private String sortBy;
	private String direction;

	private String name;
}
