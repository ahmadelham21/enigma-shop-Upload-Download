package com.enigma.enigma_shop.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder // ini biar kalian enggak perlu instance
public class SearchCustomerRequest {
	private String name;
	private String phone;
	private String birthDate;
	private Boolean status;
}
