package com.enigma.enigma_shop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagingResponse {
	private Integer totalPages;
	private Long totalElements;
	private Integer page;
	private Integer size;
	private Boolean hasNext;
	private Boolean hasPrevious;
}
