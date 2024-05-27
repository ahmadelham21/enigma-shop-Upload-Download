package com.enigma.enigma_shop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // setter, getterm noArgsConst, AllArgConst, toString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDetailResponse {
	private String id;
	private String productId;
	private Long productPrice;
	private Integer quantity;
}
/**
        {
 "id": "905375fc-b79e-4452-b583-1af15ee32392",
 "product": {
								"id": "6099e879-3688-4104-adf4-f574e398dc89",
								"name": "Pisang Goreng",
								"price": 3000,
								"stock": 6
 		},
 "productPrice": 3000,
 "qty": 1
 },
 */