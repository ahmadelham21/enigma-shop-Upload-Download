package com.enigma.enigma_shop.repository;

import com.enigma.enigma_shop.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
}

// db_wmb
// PR
// database WWB dan ERD WMB
// menu dan menu_price di lebur jadi satu, jadi relasinya bill_detail dengan menu
// m_discount dan customer_discount di hapus aja
// jadi nanti ada 6 table aja
// m_menu, m_table, t_bill, t_bill_detail, m_trans_type, m_customer
// silahkan buat AP nya, mau disamakan dengan enima-shop enggak papa, logic bebas kalian yg penting bisa melakukan transaction
// Kalau bisa semua pakai DTO aja, tapi kalau engak bisa, minimal di bill dan bill_detailnya untuk DTOnya
// deadlinenya kapan? setelah springboot selesai
// commit per fitur ya, buat branch, bisa bisa ge lihat progres kalian.
