package com.juan.bank.mod.balance.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Mendoza
 * 19/2/2023
 */

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {

  Balance findByAccountId(Long id);

  boolean existsByAccountId(Long id);
}
