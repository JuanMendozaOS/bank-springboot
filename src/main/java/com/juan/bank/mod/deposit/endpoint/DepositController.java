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

  private final DepositService depositService;
  private final AccountService accountService;
  private final CurrencyService currencyService;
  private final BalanceService balanceService;
  private final MovementService movementService;

  @Autowired
  public DepositController(DepositService depositService, AccountService accountService, CurrencyService currencyService, BalanceService balanceService, MovementService movementService) {
    this.depositService = depositService;
    this.accountService = accountService;
    this.currencyService = currencyService;
    this.balanceService = balanceService;
    this.movementService = movementService;
  }

  @PostMapping("/deposits")
  public ResponseEntity<Deposit> create(@RequestBody Deposit deposit) {
    if (containsNullOrEmpty(deposit)) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    if (deposit.getAmount().compareTo(BigDecimal.ZERO) < 1) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Account account = accountService.findById(deposit.getAccount().getId());
    Currency currency = currencyService.findByIsoCode(deposit.getCurrencyIsoCode());
    Balance balance = balanceService.findByAccountId(account.getId());

    // Verificar si no existe el account o moneda
    if (account == null || currency == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    updateBalance(balance, deposit.getAmount());
    Deposit entity = createDeposit(account, deposit, currency);
    createMovement(entity);

    return new ResponseEntity<>(entity, HttpStatus.OK);
  }

  private Deposit createDeposit(Account account, Deposit deposit, Currency currency) {
    Deposit entity = new Deposit();
    entity.setDate(LocalDateTime.now());
    entity.setAccount(account);
    entity.setIban(account.getIban());
    entity.setAmount(deposit.getAmount());
    entity.setCurrencyIsoCode(currency.getIsoCode());
    return depositService.create(entity);
  }

  private void updateBalance(Balance balance, BigDecimal transferAmount) {
    BigDecimal balanceAmount = balance.getAmount();
    balance.setAmount(balanceAmount.add(transferAmount));
    balance.setLastModified(LocalDateTime.now());
    balanceService.update(balance);
  }

  private boolean containsNullOrEmpty(Deposit deposit) {
    if (deposit == null || deposit.getAccount() == null
            || Objects.equals(deposit.getAmount(), null)
            || deposit.getCurrencyIsoCode() == null) {
      return true;
    }
    if (deposit.getCurrencyIsoCode().isEmpty()) {
      return true;
    }
    return false;
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
