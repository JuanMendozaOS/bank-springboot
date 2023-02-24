package com.juan.bank.mod.account.endpoint;

import com.juan.bank.mod.account.model.Account;
import com.juan.bank.mod.account.model.AccountService;
import com.juan.bank.mod.account.model.AccountType;
import com.juan.bank.mod.account.model.AccountTypeService;
import com.juan.bank.mod.balance.model.Balance;
import com.juan.bank.mod.balance.model.BalanceService;
import com.juan.bank.mod.bank.model.Bank;
import com.juan.bank.mod.bank.model.BankService;
import com.juan.bank.mod.currency.model.Currency;
import com.juan.bank.mod.currency.model.CurrencyService;
import com.juan.bank.mod.customer.model.Customer;
import com.juan.bank.mod.customer.model.CustomerService;
import com.juan.bank.mod.deposit.model.Deposit;
import com.juan.bank.mod.deposit.model.DepositService;
import com.juan.bank.mod.withdrawal.model.Withdrawal;
import com.juan.bank.mod.withdrawal.model.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Juan Mendoza 17/2/2023
 */

@RestController
@RequestMapping("/accounts")
public class AccountController {

  @Autowired
  private AccountService accountService;
  @Autowired
  private WithdrawalService withdrawalService;
  @Autowired
  private BalanceService balanceService;
  @Autowired
  private DepositService depositService;
  @Autowired
  private BankService bankService;
  @Autowired
  private CustomerService customerService;
  @Autowired
  private AccountTypeService accountTypeService;
  @Autowired
  private CurrencyService currencyService;

  @GetMapping("/{id}")
  public Account findById(@PathVariable("id") Long id) {
    return accountService.findById(id);
  }

  @GetMapping("{accountId}/withdrawals")
  public List<Withdrawal> findAllAccountWithdrawals(@PathVariable("accountId") Long accountId) {
    return withdrawalService.findAllAccountWithdrawals(accountId);
  }

  @GetMapping("{accountId}/balance")
  public Balance getBalance(@PathVariable("accountId") Long accountId) {
    return balanceService.findByAccountId(accountId);
  }

  @GetMapping("{accountId}/deposits")
  public List<Deposit> findAllAccountDeposits(@PathVariable("accountId") Long accountId) {
    return depositService.findAllAccountDeposits(accountId);
  }

  @GetMapping
  public List<Account> findAll() {
    return accountService.findAll();
  }

  @PostMapping
  public ResponseEntity<Account> create(@RequestBody Account account) {
    try {
      if (account == null || account.getIban() == null || account.getIban().isEmpty()
              || accountService.existsByIban(account.getIban())) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      Bank bank = bankService.findById(account.getBank().getId());
      Customer customer = customerService.findById(account.getCustomer().getId());
      Currency currency = currencyService.findByIsoCode(account.getCurrency().getIsoCode());
      AccountType accountType = accountTypeService.findById(account.getAccountType().getId());

      if (bank == null || customer == null || currency == null || accountType == null
              || !bank.isEnabled() || !customer.isEnabled()) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

      Account entity = new Account();
      entity.setIban(account.getIban());
      entity.setCustomer(customer);
      entity.setBank(bank);
      entity.setAccountType(accountType);
      entity.setCurrency(currency);
      entity.setCurrencyIsoCode(currency.getIsoCode());
      entity.setEnabled(true);
      entity = accountService.create(entity);

      createBalance(entity);
      return new ResponseEntity<>(entity, HttpStatus.OK);

    }catch (Exception e){
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  private void createBalance(Account account) {
    try {
      Balance balance = new Balance();
      balance.setAmount(BigDecimal.ZERO);
      balance.setBeginDate(LocalDateTime.now());
      balance.setLastModified(LocalDateTime.now());
      balance.setAccount(account);
      balance.setCustomer(account.getCustomer());
      balance.setCurrency(account.getCurrency());
      balance.setEnabled(true);
      balanceService.create(balance);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Account> updateEnabled(@PathVariable("id") Long id, @RequestBody Account account) {
    try {
      if (!accountService.existsById(id)) {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
      }

      // si hay buscar entidad en DB
      Account entity = accountService.findById(id);
      if (entity == null) {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
      }

      entity.setEnabled(account.isEnabled());
      entity = accountService.update(entity);
      return new ResponseEntity<>(entity, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
