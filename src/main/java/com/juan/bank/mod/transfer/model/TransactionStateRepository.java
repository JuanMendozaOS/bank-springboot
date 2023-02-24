package com.juan.bank.mod.transfer.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Mendoza 20/2/2023
 */
@Repository
public interface TransactionStateRepository extends JpaRepository<TransactionState, Long> {
  String findNameById(Long id);
}
