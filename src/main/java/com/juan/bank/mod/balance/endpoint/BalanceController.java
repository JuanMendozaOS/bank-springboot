package com.juan.bank.mod.balance.endpoint;

import com.juan.bank.mod.balance.model.Balance;
import com.juan.bank.mod.balance.model.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author Juan Mendoza
 * 19/2/2023
 */

@RestController
@RequestMapping
public class BalanceController {

  @Autowired
  private BalanceService balanceService;

  // TODO POST request method
  public ResponseEntity<Balance> addBalance(@RequestBody Balance balance){
    Balance entity = new Balance();
    entity.setAmount(balance.getAmount());
    entity.setBeginDate(LocalDateTime.now());
    entity.setLastModified(LocalDateTime.now());
    entity.setAccount(balance.getAccount());
    entity.setCustomer(balance.getCustomer());
    entity.setCurrency(balance.getCurrency());
    entity.setEnabled(true);
    entity = balanceService.create(entity);
    return new ResponseEntity<>(entity, HttpStatus.OK);
  }
}
