package com.juan.bank.mod.customer.endpoint;

import com.juan.bank.mod.customer.model.Customer;
import com.juan.bank.mod.customer.model.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Juan Mendoza 15/2/2023
 */
@RestController
@RequestMapping("/customers")
public class CustomerController {

  @Autowired
  private CustomerService customerService;

  // TODO añadir validaciones
  @GetMapping
  public List<Customer> findAll() {
    return customerService.findAll();
  }

  @GetMapping("/{id}")
  public Customer findById(@PathVariable("id") Long id) {
    return customerService.findById(id);
  }

  @PostMapping
  public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
    Customer entity = new Customer();
    entity.setFirstName(customer.getFirstName());
    entity.setLastName(customer.getLastName());
    entity.setEmail(customer.getEmail());
    entity.setPhoneNumber(customer.getPhoneNumber());
    entity.setDocumentNumber(customer.getDocumentNumber());
    entity.setEnabled(true);

    // como funciona la relación??
    entity.setDocumentType(customer.getDocumentType());
    entity = customerService.create(entity);
    return new ResponseEntity<>(entity, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer) {
    Customer entity = customerService.findById(id);
    entity.setEmail(customer.getEmail());
    entity.setPhoneNumber(customer.getPhoneNumber());
    entity = customerService.update(entity);
    return new ResponseEntity<>(entity, HttpStatus.OK);
  }
}
