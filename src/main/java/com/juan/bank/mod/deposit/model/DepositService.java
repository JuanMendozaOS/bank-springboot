package com.juan.bank.mod.deposit.model;

import com.juan.bank.app.model.EntityServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Juan Mendoza 20/2/2023
 */
@Service
public class DepositService implements EntityServiceInterface<Deposit> {

  @Autowired
  private DepositRepository depositRepository;

  @Override
  public Deposit create(Deposit entity) {
    return depositRepository.save(entity);
  }

  @Override
  public Deposit update(Deposit entity) {
    return depositRepository.save(entity);
  }

  @Override
  public Deposit findById(Long id) {
    Optional<Deposit> optionalDeposit;
    optionalDeposit = depositRepository.findById(id);
    return optionalDeposit.orElse(null);
  }

  @Override
  public List<Deposit> findAll() {
    return depositRepository.findAll();
  }

  public List<Deposit> findAllAccountDeposits(Long accountId) {
    return depositRepository.findAllDepositByAccountId(accountId);
  }
}
