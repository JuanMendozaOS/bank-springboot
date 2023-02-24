package com.juan.bank.mod.deposit.endpoint;

import com.juan.bank.mod.account.model.Account;
import com.juan.bank.mod.account.model.AccountService;
import com.juan.bank.mod.account.model.Movement;
import com.juan.bank.mod.account.model.MovementService;
import com.juan.bank.mod.balance.model.Balance;
import com.juan.bank.mod.balance.model.BalanceService;
import com.juan.bank.mod.currency.model.Currency;
import com.juan.bank.mod.currency.model.CurrencyService;
import com.juan.bank.mod.deposit.model.Deposit;
import com.juan.bank.mod.deposit.model.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Juan Mendoza 20/2/2023
 */
@RestController
@RequestMapping
public class DepositController {

  @Autowired
  private DepositService depositService;
  @Autowired
  private AccountService accountService;
  @Autowired
  private CurrencyService currencyService;
  @Autowired
  private BalanceService balanceService;
  @Autowired
  private MovementService movementService;

  @PostMapping("/deposits")
  public ResponseEntity<Deposit> create(@RequestBody Deposit deposit){

    if (deposit == null || deposit.getAccount() == null
            || Objects.equals(deposit.getAmount(), null) || deposit.getAmount().compareTo(BigDecimal.ZERO) <= 0
            || deposit.getCurrencyIsoCode() == null || deposit.getCurrencyIsoCode().isEmpty()) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Account account = accountService.findById(deposit.getAccount().getId());
    Currency currency = currencyService.findByIsoCode(deposit.getCurrencyIsoCode());
    Balance balance = balanceService.findByAccountId(account.getId());

    // Verificar si no existe el account o moneda
    if(account == null || currency == null){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    BigDecimal balanceAmount = balance.getAmount();
    balance.setAmount(balanceAmount.add(deposit.getAmount()));
    balance.setLastModified(LocalDateTime.now());
    balanceService.update(balance);

    Deposit entity = new Deposit();
    entity.setDate(LocalDateTime.now());
    entity.setAccount(account);
    entity.setIban(account.getIban());
    entity.setAmount(deposit.getAmount());
    entity.setCurrencyIsoCode(currency.getIsoCode());
    // TODO documentState
    entity = depositService.create(entity);

    createMovement(entity);
    return new ResponseEntity<>(entity, HttpStatus.OK);
  }

  private void createMovement(Deposit deposit) {
    try {
      Movement movement = new Movement();
      movement.setAccount(deposit.getAccount());
      movement.setDeposit(deposit);
      movement.setDateTime(deposit.getDate());
      movement.setAmount(deposit.getAmount());
      movement.setBalance(balanceService.findByAccountId(deposit.getAccount().getId()));
      movement.setCurrencyIsoCode(deposit.getCurrencyIsoCode());
      movementService.create(movement);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
