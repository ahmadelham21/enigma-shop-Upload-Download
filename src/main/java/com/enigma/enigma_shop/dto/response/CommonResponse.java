package com.enigma.enigma_shop.dto.response;

import com.enigma.enigma_shop.entity.Customer;
import com.enigma.enigma_shop.entity.Product;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonResponse<T> {
	private Integer statusCode;
	private String message;
	private T data; // nanti datanya bisa dinamis, array atau object
	private PagingResponse paging;
}

//class Test {
//	public static void main(String[] args) {
//		CommonResponse<Product> response = new CommonResponse<>();
//		response.setData(new Product());
//	}
//}