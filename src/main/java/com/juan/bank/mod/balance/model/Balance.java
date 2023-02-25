package com.juan.bank.mod.balance.model;

import com.juan.bank.mod.account.model.Account;
import com.juan.bank.mod.currency.model.Currency;
import com.juan.bank.mod.customer.model.Customer;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Juan Mendoza
 * 19/2/2023
 */

@Table
@Entity
public class Balance {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private BigDecimal amount;
  private LocalDateTime beginDate;
  private LocalDateTime lastModified;
  private boolean enabled;

  @OneToOne
  private Account account;
  @ManyToOne
  private Customer customer;
  @ManyToOne
  private Currency currency;


  public Balance(){}

  public Balance(Long id, BigDecimal amount, LocalDateTime beginDate, LocalDateTime lastModified, boolean enabled, Account account, Customer customer, Currency currency) {
    this.id = id;
    this.amount = amount;
    this.beginDate = beginDate;
    this.lastModified = lastModified;
    this.enabled = enabled;
    this.account = account;
    this.customer = customer;
    this.currency = currency;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public LocalDateTime getBeginDate() {
    return beginDate;
  }

  public void setBeginDate(LocalDateTime beginDate) {
    this.beginDate = beginDate;
  }

  public LocalDateTime getLastModified() {
    return lastModified;
  }

  public void setLastModified(LocalDateTime lastModified) {
    this.lastModified = lastModified;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  @Override
  public String toString() {
    return "Balance{" +
            "id=" + id +
            ", amount=" + amount +
            ", begin_date=" + beginDate +
            ", last_modified=" + lastModified +
            ", account=" + account +
            ", customer=" + customer +
            ", currency=" + currency +
            '}';
  }
}
