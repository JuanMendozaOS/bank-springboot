package com.juan.bank.mod.bank.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *
 * @author Juan Mendoza
 */
@Entity
@Table(name = "Bank")
public class Bank {

  // fields
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  
  @Column(nullable = false)
  private String code;
  
  @Column(nullable = false)
  private String name;

  @Column
  private String description;

  @Column
  private boolean enabled;

  //constructor
  public Bank() {

  }

  public Bank(Long id, String code, String name, String description, boolean enabled) {
    this.id = id;
    this.code = code;
    this.name = name;
    this.description = description;
    this.enabled = enabled;
  }

  // methods
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
  
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
  

  @Override
  public String toString() {
    return "BankEntity{" + "id=" + id + ", name=" + name + ", description=" + description + '}';
  }

}
