package com.enigma.enigma_shop.repository;

import com.enigma.enigma_shop.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, String > {
	Optional<UserAccount> findByUsername(String username);
}
