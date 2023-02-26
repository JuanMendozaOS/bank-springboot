package com.juan.bank.mod.user.endpoint;

import com.juan.bank.mod.customer.model.Customer;
import com.juan.bank.mod.customer.model.CustomerService;
import com.juan.bank.mod.user.model.User;
import com.juan.bank.mod.user.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Juan Mendoza 11/2/2023
 */
@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;
  @Autowired
  private CustomerService customerService;

  @GetMapping("/{id}")
  public User findById(@PathVariable("id") Long id) {
    return userService.findById(id);
  }

  @GetMapping
  public List<User> findAll() {
    return userService.findAll();
  }

  @PostMapping
  public ResponseEntity<User> create(@RequestBody User user) {

    if (containsNullOrEmpty(user)) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    if (userExists(user)){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(createUser(user), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<User> update(@PathVariable("id") Long id, @RequestBody User user) {
    User entity = userService.findById(id);
    if (user == null || entity == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    if (validateUpdateExists(user, id)){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(updateUser(entity, user), HttpStatus.OK);
  }

  private User updateUser(User entity, User user) {
    if (user.getUsername() != null && !user.getUsername().isEmpty()){
      entity.setUsername(user.getUsername());
    }
    if (user.getEmail() != null && !user.getEmail().isEmpty()){
      entity.setUsername(user.getEmail());
    }
    if (user.getPassword() != null && !user.getPassword().isEmpty()){
      entity.setPassword(user.getPassword());
    }
    return userService.update(entity);
  }

  private boolean validateUpdateExists(User user, Long id) {
    if(userService.existsByUsername(user.getUsername())
            && !userService.findByUsername(user.getUsername()).getId().equals(id)){
      return true;
    }
    if (userService.existsByEmail(user.getEmail())
            && !userService.findByEmail(user.getEmail()).getId().equals(id)){
      return true;
    }
    return false;
  }

  private User createUser(User user) {
    Customer customer = customerService.findById(user.getCustomer().getId());

    User entity = new User();
    entity.setUsername(user.getUsername());
    entity.setEmail(user.getEmail());
    entity.setPassword(user.getPassword());
    entity.setCreationDate(LocalDateTime.now());
    entity.setCustomer(customer);
    entity.setEnabled(true);
    return userService.create(entity);
  }

  private boolean userExists(User user) {
    if (userService.existsByUsername(user.getUsername()) || userService.existsByEmail(user.getEmail())){
      return true;
    }
    return false;
  }

  private boolean containsNullOrEmpty(User user) {
    if (user == null || user.getUsername() == null || user.getEmail() == null
            || user.getPassword() == null || user.getCustomer() == null) {
      return true;
    }
    if (user.getUsername().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()){
      return true;
    }
    return false;
  }



}
