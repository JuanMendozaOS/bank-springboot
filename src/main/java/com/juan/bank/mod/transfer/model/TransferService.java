package com.juan.bank.mod.transfer.model;

import com.juan.bank.app.model.EntityServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Juan Mendoza 20/2/2023
 */
@Service
public class TransferService implements EntityServiceInterface<Transfer> {

  @Autowired
  private TransferRepository transferRepo;

  @Override
  public Transfer create(Transfer entity) {
    return transferRepo.save(entity);
  }

  @Override
  public Transfer update(Transfer entity) {
    return transferRepo.save(entity);
  }

  @Override
  public Transfer findById(Long id) {
    Optional<Transfer> transferOptional;
    transferOptional = transferRepo.findById(id);
    return transferOptional.orElse(null);
  }

  @Override
  public List<Transfer> findAll() {
    return transferRepo.findAll();
  }

  public List<Transfer> findAllAccountTransfersByIban(String iban) {
    return transferRepo.findAllByFromIbanOrToIban(iban, iban);
  }

//  public List<Transfer> findAllTransferByAccountId(Long accountId) {
//    return transferRepo.findAllTransferByAccountId(accountId);
//  }
}
