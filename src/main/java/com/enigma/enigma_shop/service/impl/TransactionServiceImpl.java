package com.enigma.enigma_shop.service.impl;

import com.enigma.enigma_shop.dto.request.TransactionRequest;
import com.enigma.enigma_shop.dto.response.TransactionDetailResponse;
import com.enigma.enigma_shop.dto.response.TransactionResponse;
import com.enigma.enigma_shop.entity.Customer;
import com.enigma.enigma_shop.entity.Product;
import com.enigma.enigma_shop.entity.Transaction;
import com.enigma.enigma_shop.entity.TransactionDetail;
import com.enigma.enigma_shop.repository.TransactionDetailRepository;
import com.enigma.enigma_shop.repository.TransactionRepository;
import com.enigma.enigma_shop.service.CustomerService;
import com.enigma.enigma_shop.service.ProductService;
import com.enigma.enigma_shop.service.TransactionDetailService;
import com.enigma.enigma_shop.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
	private final TransactionRepository transactionRepository;
//	private TransactionDetailRepository transactionDetailRepository; // salah
	private final TransactionDetailService transactionDetailService;
	private final CustomerService customerService;
	private final ProductService productService;

	@Transactional(rollbackFor = Exception.class) // ini akan auto commit, trus nanti di rollback kalau ada Exeption
	@Override
	public TransactionResponse create(TransactionRequest request) {
		// cari dan validasi
		Customer customer = customerService.getById(request.getCustomerId());

		// 1 save ke transaction
		Transaction trx = Transaction.builder()
						.customer(customer)
						.transDate(new Date())
						.build();
		transactionRepository.saveAndFlush(trx);
		log.info("Check detail dari trxDetail: {}",trx.getCustomer());


		// 2 save ke transactionDetail
		List<TransactionDetail> trxDetail = request.getTransactionDetails().stream()
						.map(detailRequest -> {
							/**
							* Log:
								* 1. info
								* 2. debug
								* 3. warning
								* 4. error
								* */

							log.info("Quantity dari detail request: {}",detailRequest.getQty());
							Product product = productService.getById(detailRequest.getProductId());

							if (product.getStock() - detailRequest.getQty() < 0) {
								throw new RuntimeException("Out of stock");
							}

							product.setStock(product.getStock() - detailRequest.getQty());

							return TransactionDetail.builder()
											.product(product)
											.transaction(trx)
											.qty(detailRequest.getQty())
											.productPrice(product.getPrice())
											.build();
						}).toList();
		transactionDetailService.createBulk(trxDetail);
		trx.setTransactionDetails(trxDetail);

//		List<TransactionDetail> trxDetail = request.getTransactionDetail().stream()
//						.map(detailRequest -> {
//							Product product = productService.getById(detailRequest.getProductId());
//
//							if (product.getStock() - detailRequest.getQty() < 0) {
//								throw new RuntimeException("Out of stock");
//							}
//
//							product.setStock(product.getStock() - detailRequest.getQty());
//
//							return TransactionDetail.builder()
//											.product(product)
//											.transaction(trx)
//											.qty(detailRequest.getQty())
//											.productPrice(product.getPrice())
//											.build();
//						}).toList();
		List<TransactionDetailResponse> trxDetailResponse = trxDetail.stream()
						.map(detail -> {

							return TransactionDetailResponse.builder()
											.id(detail.getId())
											.productId(detail.getProduct().getId())
											.productPrice(detail.getProductPrice())
											.quantity(detail.getQty())
											.build();
						}).toList();

//		Transaction trx = Transaction.builder()
//						.customer(customer)
//						.transDate(new Date())
//						.build();

		return TransactionResponse.builder()
						.id(trx.getId())
						.customerId(trx.getCustomer().getId())
						.transDate(trx.getTransDate())
						.transactionDetails(trxDetailResponse)
						.build();

	}

	@Transactional(readOnly = true)
	@Override
	public List<TransactionResponse> getAll() {
		List<Transaction> transactions = transactionRepository.findAll();

		return transactions.stream().map(trx -> {
							List<TransactionDetailResponse> trxDetailResponse = trx.getTransactionDetails().stream().map(detail -> {
												return TransactionDetailResponse.builder()
																.id(detail.getId())
																.productId(detail.getProduct().getId())
																.productPrice(detail.getProductPrice())
																.quantity(detail.getQty())
																.build();
											}).toList();

						return TransactionResponse.builder()
										.id(trx.getId())
										.customerId(trx.getCustomer().getId())
										.transDate(trx.getTransDate())
										.transactionDetails(trxDetailResponse)
										.build();
						}).toList();



	}
}
