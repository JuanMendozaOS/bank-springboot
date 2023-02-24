package com.juan.bank.mod.account.model;

import com.juan.bank.mod.bank.model.Bank;
import com.juan.bank.mod.currency.model.Currency;
import com.juan.bank.mod.customer.model.Customer;
import jakarta.persistence.*;

/**
 * @author Juan Mendoza 17/2/2023
 */

@Entity
@Table
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  
  private String iban;
 
  @ManyToOne
  private Customer customer;
  @ManyToOne
  private AccountType accountType;

  private String currencyIsoCode; 
  
  @ManyToOne
  private Currency currency;

  @ManyToOne
  private Bank bank;
  
  private boolean enabled;

  
  public Account(){}
  
  public Account(Long id, String iban, Customer customer, AccountType accountType, Currency currency, String currencyIsoCode, Bank bank, boolean enabled) {
    this.id = id;
    this.iban = iban;
    this.customer = customer;
    this.accountType = accountType;
    this.currency = currency;
    this.currencyIsoCode = currencyIsoCode;
    this.bank = bank;
    this.enabled = enabled;
  }

  
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getIban() {
    return iban;
  }

  public void setIban(String iban) {
    this.iban = iban;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public AccountType getAccountType() {
    return accountType;
  }

  public void setAccountType(AccountType accountType) {
    this.accountType = accountType;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public String getCurrencyIsoCode() {
    return currencyIsoCode;
  }

  public void setCurrencyIsoCode(String currencyIsoCode) {
    this.currencyIsoCode = currencyIsoCode;
  }

  public Bank getBank() {
    return bank;
  }

  public void setBank(Bank bank) {
    this.bank = bank;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
  
  

}
