package com.juan.bank.mod.customer.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Mendoza
 */

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  Customer findByDocumentNumber(String fromDocumentNumber);
}
