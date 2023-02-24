package com.juan.bank.mod.customer.model;

import jakarta.persistence.*;

/**
 * @author Juan Mendoza 16/2/2023
 */
@Entity
@Table
public class DocumentType {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;
  private String description;

  public DocumentType() {}

  public DocumentType(Long id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
