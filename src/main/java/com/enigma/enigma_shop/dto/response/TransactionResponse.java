package com.enigma.enigma_shop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
	private String id;
	private String customerId;
	private Date transDate;
	private List<TransactionDetailResponse> transactionDetails;
}
