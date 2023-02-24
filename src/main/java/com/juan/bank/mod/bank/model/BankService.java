package com.juan.bank.mod.bank.model;

import com.juan.bank.app.model.EntityServiceInterface;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan Mendoza
 */
@Service
public class BankService implements EntityServiceInterface<Bank> {

  @Autowired
  public BankRepository bankRepo;

  @Override
  public Bank create(Bank entity) {
    return bankRepo.save(entity);
  }

  @Override
  public Bank update(Bank entity) {
    return bankRepo.save(entity);
  }

  @Override
  public Bank findById(Long id) {
    Optional<Bank> bankOptional;
    bankOptional = bankRepo.findById(id);
    return bankOptional.orElse(null);
  }

  @Override
  public List<Bank> findAll() {
    System.out.println(bankRepo.findAll());
    return bankRepo.findAll();
  }

  public boolean existsByNameIgnoreCase(String name) {
    return bankRepo.existsByNameIgnoreCase(name);
  }

  public Bank findByNameIgnoreCase(String name){
    return bankRepo.findByNameIgnoreCase(name);
  }

  public boolean existsByCode(String code) {
    return bankRepo.existsByCode(code);
  }
  
  public boolean existsById(Long id){
    return bankRepo.existsById(id);
  }

  public Bank findByCode(String fromBankCode) {
    return bankRepo.findByCode(fromBankCode);
  }
}
