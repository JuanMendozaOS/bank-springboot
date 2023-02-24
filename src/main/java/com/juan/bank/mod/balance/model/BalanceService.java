package com.juan.bank.mod.balance.model;

import com.juan.bank.app.model.EntityServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Juan Mendoza
 * 19/2/2023
 */

@Service
public class BalanceService implements EntityServiceInterface<Balance> {

  @Autowired
  private BalanceRepository balanceRepo;
  @Override
  public Balance create(Balance entity) {
    return balanceRepo.save(entity);
  }

  @Override
  public Balance update(Balance entity) {
    return balanceRepo.save(entity);
  }

  @Override
  public Balance findById(Long id) {
    Optional<Balance> optionalBalance;
    optionalBalance = balanceRepo.findById(id);
    return optionalBalance.orElse(null);
  }

  @Override
  public List<Balance> findAll() {
    return balanceRepo.findAll();
  }

  public Balance findByAccountId(Long id) {
    return balanceRepo.findByAccountId(id);
  }

  public boolean existsByAccountId(Long id) {
    return balanceRepo.existsByAccountId(id);
  }
}
