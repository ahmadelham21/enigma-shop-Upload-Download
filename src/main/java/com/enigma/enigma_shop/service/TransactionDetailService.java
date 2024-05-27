package com.enigma.enigma_shop.service;

import com.enigma.enigma_shop.entity.TransactionDetail;

import java.util.List;

public interface TransactionDetailService {

	List<TransactionDetail> createBulk (List<TransactionDetail> transactionDetails);
}
