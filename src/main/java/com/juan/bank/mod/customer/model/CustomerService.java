package com.juan.bank.mod.customer.model;

import com.juan.bank.app.model.EntityServiceInterface;
import com.juan.bank.mod.account.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Juan Mendoza
 */
@Service
public class CustomerService implements EntityServiceInterface<Customer> {

    @Autowired
    CustomerRepository customerRepo;

    @Override
    public Customer create(Customer entity) {
        return customerRepo.save(entity);
    }

    @Override
    public Customer update(Customer entity) {
        return customerRepo.save(entity);
    }

    @Override
    public Customer findById(Long id) {
        Optional<Customer> customerOptional;
        customerOptional = customerRepo.findById(id);
        return customerOptional.orElse(null);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepo.findAll();
    }

    public Customer findByDocumentNumber(String fromDocumentNumber) {
        return customerRepo.findByDocumentNumber(fromDocumentNumber);
    }

    public boolean existsByEmail(String email) {
        return customerRepo.existsByEmail(email);
    }

    public Account findByEmail(String email) {
        return customerRepo.findByEmail(email);
    }

    public boolean existsByDocumentNumberAndDocumentType(String documentNumber, DocumentType documentType) {
        return customerRepo.existsByDocumentNumberAndDocumentType(documentNumber, documentType);
    }
}
