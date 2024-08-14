package com.brodygaudel.ebank.repositories;

import com.brodygaudel.ebank.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("select c from Customer c where c.firstname like :keyword or c.name like :keyword")
    List<Customer> search(@Param("keyword") String keyword);

    @Query("select case when count(c)>0 then true else false END from Customer c where c.cin=?1")
    Boolean checkIfCinExists(String cin);
}
