package com.enigma.enigma_shop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponse {
	private String username;
	private List<String> roles;
}

//"roles": [
//		ROLE_CUSTOMER
//				]