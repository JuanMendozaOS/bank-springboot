package com.juan.bank.mod.customer.model;

import jakarta.persistence.*;

/**
 * @author Juan Mendoza
 */

@Entity
@Table
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private String documentNumber;
  private boolean enabled;

  @ManyToOne
  @JoinColumn(name = "document_type_id")
  private DocumentType documentType;
  private String documentTypeName;

  public Customer() {
  }

  public Customer(Long id, String firstName, String lastName, String email, String phoneNumber, String documentNumber, DocumentType documentType, boolean enabled, String documentTypeName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.documentNumber = documentNumber;
    this.documentType = documentType;
    this.enabled = enabled;
    this.documentTypeName = documentTypeName;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getDocumentNumber() {
    return documentNumber;
  }

  public void setDocumentNumber(String documentNumber) {
    this.documentNumber = documentNumber;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public DocumentType getDocumentType() {
    return documentType;
  }

  public void setDocumentType(DocumentType documentType) {
    this.documentType = documentType;
  }

  public String getDocumentTypeName() {
    return documentTypeName;
  }

  public void setDocumentTypeName(String documentTypeName) {
    this.documentTypeName = documentTypeName;
  }

  @Override
  public String toString() {
    return "Customer{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", documentNumber='" + documentNumber + '\'' +
            ", documentType=" + documentType +
            ", enabled=" + enabled +
            '}';
  }

}
