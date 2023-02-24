package com.juan.bank.mod.account.model;

import com.juan.bank.app.model.EntityServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Juan Mendoza
 * 16/2/2023
 */
@Service
public class AccountTypeService implements EntityServiceInterface<AccountType> {

  @Autowired
  private AccountTypeRepository accTypeRepo;


  @Override
  public AccountType create(AccountType entity) {
    return accTypeRepo.save(entity);
  }

  @Override
  public AccountType update(AccountType entity) {
    return accTypeRepo.save(entity);
  }

  @Override
  public AccountType findById(Long id) {
    Optional<AccountType> accountTypeOptional;
    accountTypeOptional = accTypeRepo.findById(id);
    return accountTypeOptional.orElse(null);
  }

  @Override
  public List<AccountType> findAll() {
    return accTypeRepo.findAll();
  }
}
