package com.juan.bank.mod.transfer.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Juan Mendoza 20/2/2023
 */
@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
  List<Transfer> findAllByFromIbanOrToIban(String fromIban, String toIban);
}
