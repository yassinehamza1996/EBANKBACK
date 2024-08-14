package com.brodygaudel.ebank.repositories;

import com.brodygaudel.ebank.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

    @Query("select b from BankAccount b where b.customer.id =?1")
    List<BankAccount> findByCustomerId(Long customerId);
}
