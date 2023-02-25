package com.juan.bank.mod.bank.endpoint;

import com.juan.bank.app.exception.DuplicateRecordException;
import com.juan.bank.mod.bank.model.Bank;
import com.juan.bank.mod.bank.model.BankService;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Juan Mendoza
 */
@RestController
@RequestMapping("/banks")
public class BankController {

  @Autowired
  public BankService bankService;

  @GetMapping
  public List<Bank> findAll() {
    return bankService.findAll();
  }

  @GetMapping("/{id}")
  public Bank findById(@PathVariable("id") Long id) {
    return bankService.findById(id);
  }

  @PostMapping
  public ResponseEntity<Bank> create(@RequestBody Bank bank) {
    try {
      if (containsNullOrEmpty(bank)) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      if (bankExists(bank)) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      return new ResponseEntity<>(createBank(bank), HttpStatus.OK);

    } catch (Throwable e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }


  @PutMapping("/{id}")
  public ResponseEntity<Bank> update(@PathVariable("id") Long id, @RequestBody Bank arg) {
    try {
      Bank entity = bankService.findById(id);
      if (entity == null) {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
      }

      if (bankService.existsByNameIgnoreCase(arg.getName())
              && !Objects.equals(bankService.findByNameIgnoreCase(arg.getName()).getId(), id)) {
        throw new DuplicateRecordException("Bank already exists: " + arg.getName());
      }

      return new ResponseEntity<>(updateBank(entity, arg), HttpStatus.OK);

    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  private boolean bankExists(Bank bank) {
    if (bankService.existsByNameIgnoreCase(bank.getName())) {
      throw new DuplicateRecordException("Bank already exists: " + bank.getName());
    }
    if (bankService.existsByCode(bank.getCode())) {
      throw new DuplicateRecordException("Code already exists: " + bank.getCode());
    }
    return false;
  }

  private boolean containsNullOrEmpty(Bank bank) {
    if (bank.getName() == null || bank.getName().isEmpty()) {
      return true;
    }
    if (bank.getCode() == null || bank.getCode().isEmpty()) {
      return true;
    }
    if (bank.getDescription() == null || bank.getDescription().isEmpty()) {
      return true;
    }
    return false;
  }

  private Bank createBank(Bank bank) {
    Bank entity = new Bank();
    entity.setName(bank.getName());
    entity.setCode(bank.getCode());
    entity.setDescription(bank.getDescription());
    entity.setEnabled(true);

    return bankService.create(entity);
  }


  private Bank updateBank(Bank entity, Bank arg) {
    // asignar valores de bank validados sin tocar valores no modificables (aunque lo pasen)
    if (arg.getName() != null && !arg.getName().isEmpty()) {
      entity.setName(arg.getName());
    }
    if (!(arg.getDescription() == null || arg.getDescription().equals(""))) {
      entity.setDescription(arg.getDescription());
    }
    return bankService.update(entity);
  }
}

