package com.juan.bank.mod.account.endpoint;

import com.juan.bank.mod.account.model.*;
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
import com.juan.bank.mod.transfer.model.Transfer;
import com.juan.bank.mod.transfer.model.TransferService;
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


  private final AccountService accountService;
  private final WithdrawalService withdrawalService;
  private final BalanceService balanceService;
  private final DepositService depositService;
  private final BankService bankService;
  private final CustomerService customerService;
  private final AccountTypeService accountTypeService;
  private final CurrencyService currencyService;
  private final MovementService movementService;
  private final TransferService transferService;

  @Autowired
  public AccountController(AccountService accountService, WithdrawalService withdrawalService, BalanceService balanceService, DepositService depositService, BankService bankService, CustomerService customerService, AccountTypeService accountTypeService, CurrencyService currencyService, MovementService movementService, TransferService transferService) {
    this.accountService = accountService;
    this.withdrawalService = withdrawalService;
    this.balanceService = balanceService;
    this.depositService = depositService;
    this.bankService = bankService;
    this.customerService = customerService;
    this.accountTypeService = accountTypeService;
    this.currencyService = currencyService;
    this.movementService = movementService;
    this.transferService = transferService;
  }

  @GetMapping("/{id}")
  public Account findById(@PathVariable("id") Long id) {
    return accountService.findById(id);
  }

  @GetMapping
  public List<Account> findAll() {
    return accountService.findAll();
  }

  @GetMapping("{accountId}/movements")
  public List<Movement> findAllAccountMovements(@PathVariable("accountId") Long accountId){
    return movementService.findAllAccountMovements(accountId);
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

  @GetMapping("{accountId}/transfers")
  public List<Transfer> findAllAccountTransfers(@PathVariable("accountId") Long accountId) {
    Account account = accountService.findById(accountId);
    if (account == null){
      return null;
    }
    return transferService.findAllAccountTransfersByIban(account.getIban());
  }

  @PostMapping
  public ResponseEntity<Account> create(@RequestBody Account account) {
    try {
      if (containsNullOrEmpty(account)){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      if(accountExists(account)){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

      Bank bank = bankService.findByCode("BJABCDEXXX");
      Customer customer = customerService.findById(account.getCustomer().getId());
      Currency currency = currencyService.findByIsoCode(account.getCurrencyIsoCode());
      AccountType accountType = accountTypeService.findById(account.getAccountType().getId());

      if(hasNullValue(bank, customer, currency, accountType)){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

      Account entity = createAccount(account, bank, customer, currency, accountType);
      createBalance(entity);
      return new ResponseEntity<>(entity, HttpStatus.OK);

    }catch (Exception e){
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Account> updateEnabled(@PathVariable("id") Long id, @RequestBody Account account) {
    try {
      Account entity = accountService.findById(id);
      if (entity == null) {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
      }
      return new ResponseEntity<>(update(entity, account), HttpStatus.OK);

    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  private Account update(Account entity, Account account) {
    entity.setEnabled(account.isEnabled());
    return accountService.update(entity);
  }

  private Account createAccount(Account account, Bank bank, Customer customer, Currency currency, AccountType accountType) {
    Account entity = new Account();
    entity.setIban(account.getIban());
    entity.setCustomer(customer);
    entity.setBank(bank);
    entity.setAccountType(accountType);
    entity.setCurrency(currency);
    entity.setCurrencyIsoCode(currency.getIsoCode());
    entity.setEnabled(true);
    return accountService.create(entity);
  }

  private boolean hasNullValue(Bank bank, Customer customer, Currency currency, AccountType accountType) {
    if (bank == null || customer == null || currency == null || accountType == null) {
      return true;
    }
    return false;
  }

  private boolean accountExists(Account account) {
    return accountService.existsByIban(account.getIban());
  }

  private boolean containsNullOrEmpty(Account account) {
    if (account == null || account.getIban() == null
            || account.getAccountType() == null || account.getCurrencyIsoCode() == null
            || account.getCustomer() == null){
      return true;
    }
    if (account.getCurrencyIsoCode().isEmpty() || account.getIban().isEmpty()){
      return true;
    }
    return false;
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


}
