package com.enigma.enigma_shop.dto.request;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {
	private String customerId;
	private List<TransactionDetailRequest> transactionDetails;
}
