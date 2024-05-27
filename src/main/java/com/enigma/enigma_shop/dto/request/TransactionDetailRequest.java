package com.enigma.enigma_shop.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDetailRequest {
	private String productId;
	private Integer qty;
}
