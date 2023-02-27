package com.juan.bank.mod.customer.endpoint;

import com.juan.bank.mod.customer.model.Customer;
import com.juan.bank.mod.customer.model.CustomerService;
import com.juan.bank.mod.customer.model.DocumentType;
import com.juan.bank.mod.customer.model.DocumentTypeService;
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

  private final CustomerService customerService;
  private final DocumentTypeService docTypeService;

  @Autowired
  public CustomerController(CustomerService customerService, DocumentTypeService docTypeService) {
    this.customerService = customerService;
    this.docTypeService = docTypeService;
  }

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

    if (containsNullOrEmpty(customer)){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    if(customerExists(customer)){
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    return new ResponseEntity<>(createCustomer(customer), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Customer> update(@PathVariable("id") Long id, @RequestBody Customer customer) {
    Customer entity = customerService.findById(id);
    if (hasNullValue(customer, entity)){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    // verificar que el correo al que se intenta actualizar no lo posee otro customer
    if (customerService.existsByEmail(customer.getEmail())
            && !customerService.findByEmail(customer.getEmail()).getId().equals(id)){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(updateCustomer(entity, customer), HttpStatus.OK);
  }

  private Customer updateCustomer(Customer entity, Customer customer) {
    if (customer.getEmail() != null && !customer.getEmail().isEmpty()){
      entity.setEmail(customer.getEmail());
    }
    if(customer.getPhoneNumber() != null && !customer.getPhoneNumber().isEmpty()){
      entity.setPhoneNumber(customer.getPhoneNumber());
    }
    return customerService.update(entity);
  }

  private boolean hasNullValue(Customer customer, Customer entity) {
    if (customer == null){
      return true;
    }
    if (entity == null){
      return true;
    }
    return false;
  }

  private Customer createCustomer(Customer customer) {
    Customer entity = new Customer();
    DocumentType documentType = docTypeService.findByName(customer.getDocumentTypeName());

    entity.setFirstName(customer.getFirstName());
    entity.setLastName(customer.getLastName());
    entity.setEmail(customer.getEmail());
    entity.setPhoneNumber(customer.getPhoneNumber());
    entity.setDocumentNumber(customer.getDocumentNumber());
    entity.setEnabled(true);
    entity.setDocumentTypeName(documentType.getName());
    entity.setDocumentType(documentType);
    return customerService.create(entity);
  }

  private boolean customerExists(Customer customer) {
    if(customerService.existsByEmail(customer.getEmail())){
      return true;
    }
    if (customerService.existsByDocumentNumberAndDocumentType(customer.getDocumentNumber(), customer.getDocumentType())){
      return true;
    }
    return false;
  }

  private boolean containsNullOrEmpty(Customer customer) {
    if (customer == null || customer.getFirstName() == null
            || customer.getLastName() == null || customer.getEmail() == null
            || customer.getPhoneNumber() == null || customer.getDocumentNumber() == null
            || customer.getDocumentTypeName() == null){
      return true;
    }
    if (customer.getFirstName().isEmpty() || customer.getLastName().isEmpty()
            || customer.getEmail().isEmpty() || customer.getPhoneNumber().isEmpty()
            || customer.getDocumentNumber().isEmpty() || customer.getDocumentTypeName().isEmpty()){
      return true;
    }
    return false;
  }


}
