package com.juan.bank.mod.bank.endpoint;

import com.juan.bank.app.exception.DuplicateRecordException;
import com.juan.bank.app.exception.RecordNotFoundException;
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
      // validar datos de entrada
      if (bank.getName() == null || bank.getName().isEmpty()) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      if (bank.getCode() == null || bank.getCode().isEmpty()) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

      // consistencia de creación 
      if (bankService.existsByNameIgnoreCase(bank.getName())) {
        throw new DuplicateRecordException("Bank already exists: " + bank.getName());
      }
      // existsbybankcode redundate
      if (bankService.existsByCode(bank.getCode())) {
        throw new DuplicateRecordException("Code already exists: " + bank.getCode());
      }

      // hacer lo que tengo que hacer
      Bank entity = new Bank();
      entity.setName(bank.getName());
      entity.setCode(bank.getCode());
      entity.setDescription(bank.getDescription());
      entity.setEnabled(true);

      // retornar lo que tengo que retornar o excepcionar si algo está mal
      entity = bankService.create(entity);
      return new ResponseEntity<>(entity, HttpStatus.OK);

    } catch (Throwable e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Bank> update(@PathVariable("id") Long id, @RequestBody Bank arg) {
    try {
      //primero tengo id?
      if (!bankService.existsById(id)) {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
      }

      // si hay buscar entidad en DB
      Bank entity = bankService.findById(id);
      if (entity == null) {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
      }

      // consistencia de creación
      if (bankService.existsByNameIgnoreCase(arg.getName())
              && !Objects.equals(bankService.findByNameIgnoreCase(arg.getName()).getId(), id)) {
        throw new DuplicateRecordException("Bank already exists: " + arg.getName());
      }

      // asignar valores de bank validados sin tocar valores no modificables (aunque lo pasen)
      if (arg.getName() != null && !arg.getName().isEmpty()) {
        entity.setName(arg.getName());
      }
      if (!(arg.getDescription() == null || arg.getDescription().equals(""))) {
        entity.setDescription(arg.getDescription());
      }


      entity = bankService.update(entity);
      return new ResponseEntity<>(entity, HttpStatus.OK);

    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}

