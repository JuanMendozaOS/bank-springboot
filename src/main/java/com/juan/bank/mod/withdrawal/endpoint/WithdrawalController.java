package com.juan.bank.mod.withdrawal.endpoint;

import com.juan.bank.mod.account.model.Account;
import com.juan.bank.mod.account.model.AccountService;
import com.juan.bank.mod.account.model.Movement;
import com.juan.bank.mod.account.model.MovementService;
import com.juan.bank.mod.balance.model.Balance;
import com.juan.bank.mod.balance.model.BalanceService;
import com.juan.bank.mod.currency.model.Currency;
import com.juan.bank.mod.currency.model.CurrencyService;
import com.juan.bank.mod.withdrawal.model.Withdrawal;
import com.juan.bank.mod.withdrawal.model.WithdrawalService;
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
public class WithdrawalController {

  private final WithdrawalService withdrawalService;
  private final AccountService accountService;
  private final CurrencyService currencyService;
  private final BalanceService balanceService;
  private final MovementService movementService;

  @Autowired
  public WithdrawalController(WithdrawalService withdrawalService, AccountService accountService, CurrencyService currencyService, BalanceService balanceService, MovementService movementService) {
    this.withdrawalService = withdrawalService;
    this.accountService = accountService;
    this.currencyService = currencyService;
    this.balanceService = balanceService;
    this.movementService = movementService;
  }

  @PostMapping("/withdrawals")
  public ResponseEntity<Withdrawal> create(@RequestBody Withdrawal withdrawal) {

    if (containsNullOrEmpty(withdrawal)) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Account account = accountService.findById(withdrawal.getAccount().getId());
    Currency currency = currencyService.findByIsoCode(withdrawal.getCurrencyIsoCode());

    if (account == null || currency == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Balance balance = balanceService.findByAccountId(account.getId());
    if (!validateWithdrawal(withdrawal, balance)){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    updateBalance(balance, withdrawal.getAmount());
    Withdrawal entity = createWithdrawal(withdrawal, account, currency);
    createMovement(entity);

    return new ResponseEntity<>(entity, HttpStatus.OK);
  }

  private Withdrawal createWithdrawal(Withdrawal withdrawal, Account account, Currency currency) {
    Withdrawal entity = new Withdrawal();
    entity.setDateTime(LocalDateTime.now());
    entity.setAccount(account);
    entity.setIban(account.getIban());
    entity.setAmount(withdrawal.getAmount());
    entity.setCurrencyIsoCode(currency.getIsoCode());
    return withdrawalService.create(entity);
  }

  private void updateBalance(Balance balance, BigDecimal amount) {
    balance.setAmount(balance.getAmount().subtract(amount));
    balance.setLastModified(LocalDateTime.now());
    balanceService.update(balance);
  }

  private boolean validateWithdrawal(Withdrawal withdrawal, Balance balance) {
    if (withdrawal.getAmount().compareTo(BigDecimal.ZERO) < 1){
      return false;
    }
    // verificar que el monto de balance no sea menor al monto a retirar
    if (withdrawal.getAmount().compareTo(balance.getAmount()) > 0) {
      return false;
    }
    return true;
  }

  private boolean containsNullOrEmpty(Withdrawal withdrawal) {
    if (withdrawal == null || withdrawal.getAccount() == null
            || Objects.equals(withdrawal.getAmount(), null)
            || withdrawal.getCurrencyIsoCode() == null) {
      return true;
    }
    if (withdrawal.getCurrencyIsoCode().isEmpty()) {
      return true;
    }
    return false;
  }

  private void createMovement(Withdrawal withdrawal) {
    try {
      Movement movement = new Movement();
      movement.setAccount(withdrawal.getAccount());
      movement.setWithdrawal(withdrawal);
      movement.setDateTime(withdrawal.getDateTime());
      movement.setAmount(withdrawal.getAmount());
      movement.setBalance(balanceService.findByAccountId(withdrawal.getAccount().getId()));
      movement.setCurrencyIsoCode(withdrawal.getCurrencyIsoCode());
      movementService.create(movement);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
