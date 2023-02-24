package com.juan.bank.mod.account.model;

import com.juan.bank.mod.balance.model.Balance;
import com.juan.bank.mod.deposit.model.Deposit;
import com.juan.bank.mod.transfer.model.Transfer;
import com.juan.bank.mod.withdrawal.model.Withdrawal;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Juan Mendoza 21/2/2023
 */
@Entity
@Table
public class Movement {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  private Account account;
  @ManyToOne
  private Deposit deposit;
  @ManyToOne
  private Transfer transfer;
  @ManyToOne
  private Withdrawal withdrawal;
  @ManyToOne
  private Balance balance;

  private LocalDateTime dateTime;
  private BigDecimal amount;
  private String currencyIsoCode;
  private boolean isReverseTransfer;

  public Movement(){}

  public Movement(Long id, Account account, Deposit deposit, Transfer transfer, Withdrawal withdrawal, Balance balance, LocalDateTime dateTime, BigDecimal amount, String currencyIsoCode, boolean isReverseTransfer) {
    this.id = id;
    this.account = account;
    this.deposit = deposit;
    this.transfer = transfer;
    this.withdrawal = withdrawal;
    this.balance = balance;
    this.dateTime = dateTime;
    this.amount = amount;
    this.currencyIsoCode = currencyIsoCode;
    this.isReverseTransfer = isReverseTransfer;
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

  public Deposit getDeposit() {
    return deposit;
  }

  public void setDeposit(Deposit deposit) {
    this.deposit = deposit;
  }

  public Transfer getTransfer() {
    return transfer;
  }

  public void setTransfer(Transfer transfer) {
    this.transfer = transfer;
  }

  public Withdrawal getWithdrawal() {
    return withdrawal;
  }

  public void setWithdrawal(Withdrawal withdrawal) {
    this.withdrawal = withdrawal;
  }

  public Balance getBalance() {
    return balance;
  }

  public void setBalance(Balance balance) {
    this.balance = balance;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getCurrencyIsoCode() {
    return currencyIsoCode;
  }

  public void setCurrencyIsoCode(String currencyIsoCode) {
    this.currencyIsoCode = currencyIsoCode;
  }

  public boolean isReverseTransfer() {
    return isReverseTransfer;
  }

  public void setReverseTransfer(boolean reverseTransfer) {
    isReverseTransfer = reverseTransfer;
  }

  @Override
  public String toString() {
    return "Movement{" +
            "id=" + id +
            ", account=" + account +
            ", deposit=" + deposit +
            ", transfer=" + transfer +
            ", withdrawal=" + withdrawal +
            ", balance=" + balance +
            ", dateTime=" + dateTime +
            ", amount=" + amount +
            ", currencyIsoCode='" + currencyIsoCode + '\'' +
            ", isReverseTransfer=" + isReverseTransfer +
            '}';
  }
}
