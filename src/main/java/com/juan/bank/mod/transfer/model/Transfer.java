package com.juan.bank.mod.transfer.model;

import com.juan.bank.mod.customer.model.DocumentType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Juan Mendoza 20/2/2023
 */

@Entity
@Table
public class Transfer {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String fromIban;
  private String fromDocumentNumber;
  private String fromBankCode;
  @ManyToOne
  private DocumentType fromDocumentType;
  private String fromDocumentTypeName;

  private String toIban;
  private String toDocumentNumber;
  private String toBankCode;
  @ManyToOne
  private DocumentType toDocumentType;
  private String toDocumentTypeName;


  private BigDecimal amount;
  private String currencyIsoCode;
  private LocalDateTime operationDate;
  private LocalDateTime accreditationDate;
  private String transactionState;
  private Long externalTransferId;

  public Transfer(){}

  public Transfer(Long id, String fromIban, String fromDocumentNumber, String fromBankCode, DocumentType fromDocumentType, String fromDocumentTypeName, String toIban, String toDocumentNumber, String toBankCode, DocumentType toDocumentType, String toDocumentTypeName, BigDecimal amount, String currencyIsoCode, LocalDateTime operationDate, LocalDateTime accreditationDate, Long externalTransferId) {
    this.id = id;
    this.fromIban = fromIban;
    this.fromDocumentNumber = fromDocumentNumber;
    this.fromBankCode = fromBankCode;
    this.fromDocumentType = fromDocumentType;
    this.fromDocumentTypeName = fromDocumentTypeName;
    this.toIban = toIban;
    this.toDocumentNumber = toDocumentNumber;
    this.toBankCode = toBankCode;
    this.toDocumentType = toDocumentType;
    this.toDocumentTypeName = toDocumentTypeName;
    this.amount = amount;
    this.currencyIsoCode = currencyIsoCode;
    this.operationDate = operationDate;
    this.accreditationDate = accreditationDate;
    this.externalTransferId = externalTransferId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFromIban() {
    return fromIban;
  }

  public void setFromIban(String fromIban) {
    this.fromIban = fromIban;
  }

  public String getFromDocumentNumber() {
    return fromDocumentNumber;
  }

  public void setFromDocumentNumber(String fromDocumentNumber) {
    this.fromDocumentNumber = fromDocumentNumber;
  }

  public String getFromBankCode() {
    return fromBankCode;
  }

  public void setFromBankCode(String fromBankCode) {
    this.fromBankCode = fromBankCode;
  }

  public DocumentType getFromDocumentType() {
    return fromDocumentType;
  }

  public void setFromDocumentType(DocumentType fromDocumentType) {
    this.fromDocumentType = fromDocumentType;
  }

  public String getToIban() {
    return toIban;
  }

  public void setToIban(String toIban) {
    this.toIban = toIban;
  }

  public String getToDocumentNumber() {
    return toDocumentNumber;
  }

  public void setToDocumentNumber(String toDocumentNumber) {
    this.toDocumentNumber = toDocumentNumber;
  }

  public String getToBankCode() {
    return toBankCode;
  }

  public void setToBankCode(String toBankCode) {
    this.toBankCode = toBankCode;
  }

  public DocumentType getToDocumentType() {
    return toDocumentType;
  }

  public void setToDocumentType(DocumentType toDocumentType) {
    this.toDocumentType = toDocumentType;
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

  public LocalDateTime getOperationDate() {
    return operationDate;
  }

  public void setOperationDate(LocalDateTime operationDate) {
    this.operationDate = operationDate;
  }

  public LocalDateTime getAccreditationDate() {
    return accreditationDate;
  }

  public void setAccreditationDate(LocalDateTime accreditationDate) {
    this.accreditationDate = accreditationDate;
  }

  public String getTransactionState() {
    return transactionState;
  }

  public void setTransactionState(String transactionState) {
    this.transactionState = transactionState;
  }

  public Long getExternalTransferId() {
    return externalTransferId;
  }

  public void setExternalTransferId(Long externalTransferId) {
    this.externalTransferId = externalTransferId;
  }

  public String getFromDocumentTypeName() {
    return fromDocumentTypeName;
  }

  public void setFromDocumentTypeName(String fromDocumentTypeName) {
    this.fromDocumentTypeName = fromDocumentTypeName;
  }

  public String getToDocumentTypeName() {
    return toDocumentTypeName;
  }

  public void setToDocumentTypeName(String toDocumentTypeName) {
    this.toDocumentTypeName = toDocumentTypeName;
  }

  @Override
  public String toString() {
    return "Transfer{" +
            "id=" + id +
            ", fromIban='" + fromIban + '\'' +
            ", fromDocumentNumber='" + fromDocumentNumber + '\'' +
            ", fromBankCode='" + fromBankCode + '\'' +
            ", fromDocumentType=" + fromDocumentType +
            ", toIban='" + toIban + '\'' +
            ", toDocumentNumber='" + toDocumentNumber + '\'' +
            ", toBankCode='" + toBankCode + '\'' +
            ", toDocumentType=" + toDocumentType +
            ", amount=" + amount +
            ", currencyIsoCode='" + currencyIsoCode + '\'' +
            ", operationDate=" + operationDate +
            ", accreditationDate=" + accreditationDate +
            ", transactionState='" + transactionState + '\'' +
            ", externalId=" + externalTransferId +
            '}';
  }
}
