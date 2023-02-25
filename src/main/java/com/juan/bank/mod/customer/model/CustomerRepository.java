package com.juan.bank.mod.customer.model;

import com.juan.bank.mod.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Mendoza
 */

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  Customer findByDocumentNumber(String fromDocumentNumber);

  Account findByEmail(String email);

  boolean existsByEmail(String email);

  boolean existsByDocumentNumberAndDocumentType(String documentNumber, DocumentType documentType);
}
