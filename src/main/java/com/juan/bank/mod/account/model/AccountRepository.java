package com.juan.bank.mod.account.model;

import com.juan.bank.mod.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Mendoza 17/2/2023
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

  boolean existsById(Long id);

  boolean existsByIban(String iban);

  Account findByIban(String iban);

  Customer findByCustomerId(Long id);
}
