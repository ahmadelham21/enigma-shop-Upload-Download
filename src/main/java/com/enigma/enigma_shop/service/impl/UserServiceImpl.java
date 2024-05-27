package com.enigma.enigma_shop.service.impl;

import com.enigma.enigma_shop.entity.UserAccount;
import com.enigma.enigma_shop.repository.UserAccountRepository;
import com.enigma.enigma_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserAccountRepository userAccountRepository;

	@Transactional(readOnly = true)
	@Override
	// bukan kita yg manggil
	// yang menangani proses login yg dikelola oleh AuthenticationManager
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userAccountRepository.findByUsername(username)
						.orElseThrow(() -> new UsernameNotFoundException("username not found"));
	}

	@Transactional(readOnly = true)
	@Override
	public UserAccount getByUserId(String id) {
		return userAccountRepository.findById(id).orElseThrow(
						() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found")
		);
	}

	@Override
	public UserAccount getByContext() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		return userAccountRepository.findByUsername(authentication.getPrincipal().toString()).orElseThrow(
						() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found from context")
		);
	}
}
