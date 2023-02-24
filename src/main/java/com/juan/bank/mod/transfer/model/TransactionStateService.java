package com.juan.bank.mod.transfer.model;

import com.juan.bank.app.model.EntityServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Juan Mendoza 20/2/2023
 */
@Service
public class TransactionStateService implements EntityServiceInterface<TransactionState> {

  @Autowired
  private TransactionStateRepository transStateRepo;

  @Override
  public TransactionState create(TransactionState entity) {
    return transStateRepo.save(entity);
  }

  @Override
  public TransactionState update(TransactionState entity) {
    return transStateRepo.save(entity);
  }

  @Override
  public TransactionState findById(Long id) {
    Optional<TransactionState>transactionStateOptional;
    transactionStateOptional = transStateRepo.findById(id);
    return transactionStateOptional.orElse(null);
  }

  @Override
  public List<TransactionState> findAll() {
    return transStateRepo.findAll();
  }

  public String findNameById(Long id) {
    return transStateRepo.findNameById(id);
  }
}
