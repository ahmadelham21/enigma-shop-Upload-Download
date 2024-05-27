package com.enigma.enigma_shop.repository;

import com.enigma.enigma_shop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
	// query method
	// List<T> / Stream<T> findAllBy... -> List
	// Opstional<T>  findBy... -> Satuan/List
	List<Product> findAllByNameLike(String name);

	// simulasikan filter berdasarkan name, minPrive, ,maxPrice dan Stock menggunakan operaso or / and
	// jika enggak ketemu maka tampilkan semua product

	// buat full untuk customer
/**
 * Menggunakan Query Method
 * 1. Name
 * 2. Mobile Phone
 * 3. Birth Date
 * 4. Status
 *
 * untuk operasinya and atau or
 *1.  jika nama yg di cari ada, ya dapet namanya
 *2.  cuman kalau namanya enggak sesuai, tapi mobile phonenya sesuai,
 * yaudah sesuai dengan mobile phone nya
 */
}
