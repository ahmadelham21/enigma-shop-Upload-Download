package com.enigma.enigma_shop.service.impl;

import com.enigma.enigma_shop.constant.UserRole;
import com.enigma.enigma_shop.entity.Role;
import com.enigma.enigma_shop.repository.RoleRepository;
import com.enigma.enigma_shop.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
	private final RoleRepository roleRepository;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Role getOrSave(UserRole role) {

		// ngebuat role
		// disni saat regiter kita buat rolenya, kalau belum ada, rolenya dibuat, nah dikasus ini berarti rolenya ROLE_CUSTOMER, lalu kalau sudah ada, jangan buat lagi, berarti kita gunakan

		return roleRepository.findByRole(role)
						.orElseGet(() -> roleRepository.saveAndFlush(
										Role.builder()
														.role(role)
														.build()
						));
	}
}
