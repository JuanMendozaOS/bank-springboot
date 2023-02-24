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
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * @author Juan Mendoza 20/2/2023
 */
@RestController
@RequestMapping
public class WithdrawalController {

  @Autowired
  private WithdrawalService withdrawalService;
  @Autowired
  private AccountService accountService;
  @Autowired
  private CurrencyService currencyService;
  @Autowired
  private BalanceService balanceService;
  @Autowired
  private MovementService movementService;

  // TODO validar... validar
  @PostMapping("/withdrawals")
  public ResponseEntity<Withdrawal> create(@RequestBody Withdrawal withdrawal) {

    if (withdrawal == null || withdrawal.getAccount() == null
            || Objects.equals(withdrawal.getAmount(), null) || withdrawal.getAmount().compareTo(BigDecimal.ZERO) <= 0
            || withdrawal.getCurrencyIsoCode() == null || withdrawal.getCurrencyIsoCode().isEmpty()) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Account account = accountService.findById(withdrawal.getAccount().getId());
    Currency currency = currencyService.findByIsoCode(withdrawal.getCurrencyIsoCode());
    Balance balance = balanceService.findByAccountId(account.getId());

    // Verificar si no existe el account o moneda
    if(account == null || currency == null){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    BigDecimal balanceAmount = balance.getAmount();

    // verificar que el monto de balance no sea menor al monto a retirar
    if (withdrawal.getAmount().compareTo(balanceAmount) > 0){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // puede dar problemas cuando se afecta la misma columna al mismo tiempo, usar synchonized
    balance.setAmount(balanceAmount.subtract(withdrawal.getAmount()));
    balance.setLastModified(LocalDateTime.now());
    balanceService.update(balance);

    // cargar la entidad con los valores validados
    Withdrawal entity = new Withdrawal();
    entity.setDateTime(LocalDateTime.now());
    entity.setAccount(account);
    entity.setIban(account.getIban());
    entity.setAmount(withdrawal.getAmount());
    entity.setCurrencyIsoCode(currency.getIsoCode());
    entity = withdrawalService.create(entity);

    // crear movimiento luego de crear el retiro
    createMovement(entity);
    return new ResponseEntity<>(entity, HttpStatus.OK);
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
