package com.juan.bank.mod.user.model;

import com.juan.bank.mod.customer.model.Customer;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 *
 * @author Juan Mendoza
 */
@Entity
@Table(name = "app_user")
public class User {

  @Id
  @GeneratedValue(generator = "usersq")
  @SequenceGenerator(name = "usersq", sequenceName = "usersq", allocationSize = 1)
  private Long id;

  private String username;
  private String email;
  private String password;
  private LocalDateTime creationDate;
  private boolean enabled;

  // es necesario escribir @OneToOne en el entity que no posee el foreign key?
  @OneToOne
  @JoinColumn(name = "customer_id", referencedColumnName = "id")
  private Customer customer;


  public User() {
  }

  public User(Long id, String username, String email, String password, LocalDateTime creationDate, boolean enabled, Customer customer) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.creationDate = creationDate;
    this.enabled = enabled;
    this.customer = customer;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public String toString() {
    return "UserEntity{"
            + "id=" + id
            + ", username='" + username + '\''
            + ", email='" + email + '\''
            + ", password='" + password + '\''
            + ", enabled=" + enabled
            + '}';
  }
}  
