package com.juan.bank.mod.deposit.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Juan Mendoza 20/2/2023
 */

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {
  List<Deposit> findAllDepositByAccountId(Long id);
}
