package com.juan.bank.mod.account.model;

import com.juan.bank.app.model.EntityServiceInterface;
import com.juan.bank.mod.customer.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Juan Mendoza 17/2/2023
 */
@Service
public class AccountService implements EntityServiceInterface<Account> {

  @Autowired
  private AccountRepository accountRepo;


  @Override
  public Account create(Account entity) {
    return accountRepo.save(entity);
  }

  @Override
  public Account update(Account entity) {
    return accountRepo.save(entity);
  }

  @Override
  public Account findById(Long id) {
    Optional<Account> optionalAccount;
    optionalAccount = accountRepo.findById(id);
    return optionalAccount.orElse(null);
  }

  @Override
  public List<Account> findAll() {
    return accountRepo.findAll();
  }

  public boolean existsById(Long id){
    return accountRepo.existsById(id);
  }

  public boolean existsByIban(String iban) {
    return accountRepo.existsByIban(iban);
  }

  public Account findByIban(String iban) {
    return accountRepo.findByIban(iban);
  }

  public Customer findByCustomerId(Long id) {
    return accountRepo.findByCustomerId(id);
  }
}
