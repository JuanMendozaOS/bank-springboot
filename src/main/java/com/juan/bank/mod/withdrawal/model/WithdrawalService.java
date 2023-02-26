package com.juan.bank.mod.withdrawal.model;

import com.juan.bank.app.model.EntityServiceInterface;
import com.juan.bank.mod.balance.model.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Juan Mendoza 20/2/2023
 */
@Service
public class WithdrawalService implements EntityServiceInterface<Withdrawal> {

  @Autowired
  private WithdrawalRepository withdrawalRepo;

  @Override
  public Withdrawal create(Withdrawal entity) {
    return withdrawalRepo.save(entity);
  }

  @Override
  public Withdrawal update(Withdrawal entity) {
    return withdrawalRepo.save(entity);
  }

  @Override
  public Withdrawal findById(Long id) {
    Optional<Withdrawal> withdrawalOptional;
    withdrawalOptional = withdrawalRepo.findById(id);
    return withdrawalOptional.orElse(null);
  }

  @Override
  public List<Withdrawal> findAll() {
    return  withdrawalRepo.findAll();
  }

  public List<Withdrawal> findAllAccountWithdrawals(Long accountId) {
    return withdrawalRepo.findAllWithdrawalByAccountId(accountId);
  }

  public Balance findBalanceByAccountIban(String iban) {
    return withdrawalRepo.findBalanceByAccountIban(iban);
  }

}
