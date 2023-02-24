package com.juan.bank.mod.withdrawal.model;

import com.juan.bank.mod.account.model.Account;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Juan Mendoza 20/2/2023
 */
@Entity
@Table
public class Withdrawal {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  private Account account;

  private String iban;
  private BigDecimal amount;
  private LocalDateTime dateTime;
  private String currencyIsoCode;


  public Withdrawal(){}

  public Withdrawal(Long id, Account account, String iban, BigDecimal amount, LocalDateTime dateTime, String currencyIsoCode) {
    this.id = id;
    this.account = account;
    this.iban = iban;
    this.amount = amount;
    this.dateTime = dateTime;
    this.currencyIsoCode = currencyIsoCode;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public String getIban() {
    return iban;
  }

  public void setIban(String iban) {
    this.iban = iban;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public String getCurrencyIsoCode() {
    return currencyIsoCode;
  }

  public void setCurrencyIsoCode(String currencyIsoCode) {
    this.currencyIsoCode = currencyIsoCode;
  }

  @Override
  public String toString() {
    return "Withdrawal{" +
            "id=" + id +
            ", account=" + account +
            ", iban='" + iban + '\'' +
            ", amount=" + amount +
            ", date=" + dateTime +
            ", currencyIsoCode='" + currencyIsoCode + '\'' +
            '}';
  }
}
