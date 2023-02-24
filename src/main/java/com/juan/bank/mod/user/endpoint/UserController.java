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

  // TODO añadir validaciones
  @PostMapping
  public ResponseEntity<User> create(@RequestBody User user) {
    if(user == null || user.getUsername() == null || user.getUsername().isEmpty()
            || user.getEmail() == null || user.getEmail().isEmpty()
            || user.getPassword() == null || user.getPassword().isEmpty()
            || user.getCustomer() == null){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Customer customer = customerService.findById(user.getId());


    User entity = new User();
    entity.setUsername(user.getUsername());
    entity.setEmail(user.getEmail());
    entity.setPassword(user.getPassword());
    entity.setCreationDate(LocalDateTime.now());
    entity.setCustomer(customer);
    entity.setEnabled(true);
    entity = userService.create(entity);
    return new ResponseEntity<>(entity, HttpStatus.OK);
  }


  @PutMapping("/{id}")
  public ResponseEntity<User> update(@PathVariable("id") Long id, @RequestBody User user) {
    if(user == null ){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    User entity = userService.findById(id);
    // TODO terminar if(userService.existsByUsername())
    entity.setUsername(user.getUsername());
    entity.setEmail(user.getEmail());
    entity.setPassword(user.getPassword());
    entity = userService.create(entity);
    return new ResponseEntity<>(entity, HttpStatus.OK);
  }
}
