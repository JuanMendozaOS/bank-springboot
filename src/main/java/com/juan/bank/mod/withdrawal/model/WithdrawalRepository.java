package com.juan.bank.mod.withdrawal.model;

import com.juan.bank.mod.balance.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Juan Mendoza 20/2/2023
 */
@Repository
public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {

  List<Withdrawal> findAllWithdrawalByAccountId(Long accountId);

  Balance findBalanceByAccountIban(String iban);

}
