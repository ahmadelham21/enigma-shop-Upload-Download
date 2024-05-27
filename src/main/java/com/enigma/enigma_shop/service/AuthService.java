package com.enigma.enigma_shop.service;

import com.enigma.enigma_shop.dto.request.AuthRequest;
import com.enigma.enigma_shop.dto.response.LoginResponse;
import com.enigma.enigma_shop.dto.response.RegisterResponse;

public interface AuthService {
	RegisterResponse register(AuthRequest request);
	RegisterResponse registerAdmin(AuthRequest request);
	LoginResponse login(AuthRequest request);
}
