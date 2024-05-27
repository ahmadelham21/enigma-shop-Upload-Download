package com.enigma.enigma_shop.dto.response;

import com.enigma.enigma_shop.entity.UserAccount;
import lombok.Builder;
import lombok.Data;


import java.util.Date;

@Data
@Builder
public class CustomerResponse {
	private String id;
	private String name;
	private String mobilePhoneNo;
	private String address;
	private Boolean status;
	private String userAccountId;
}
