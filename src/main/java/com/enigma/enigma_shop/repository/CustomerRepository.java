package com.enigma.enigma_shop.repository;

import com.enigma.enigma_shop.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository // ini optional, tapi kita kasih aja biar konsistent
@Transactional //set autoCommit default trus, harus kita false dulu. baru setelah setelah query kita commit
public interface CustomerRepository extends JpaRepository<Customer, String>, JpaSpecificationExecutor<Customer> {
	@Modifying // harus di tambahkan anotasi ini untub merubah data
	@Query(value = "UPDATE m_customer SET status = :status WHERE id = :id", nativeQuery = true)
	void updateStatus(@Param("id") String id, @Param("status") Boolean status); // cara pakai gimana? tinggal panggil di service
}
