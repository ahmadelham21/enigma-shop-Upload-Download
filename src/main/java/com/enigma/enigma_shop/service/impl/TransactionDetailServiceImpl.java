package com.enigma.enigma_shop.service.impl;

import com.enigma.enigma_shop.entity.TransactionDetail;
import com.enigma.enigma_shop.repository.TransactionDetailRepository;
import com.enigma.enigma_shop.service.TransactionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
public class TransactionDetailServiceImpl implements TransactionDetailService {
	private final TransactionDetailRepository transactionDetailRepository;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<TransactionDetail> createBulk(List<TransactionDetail> transactionDetails) {
		return transactionDetailRepository.saveAllAndFlush(transactionDetails);
	}
}
