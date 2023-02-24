package com.juan.bank.mod.transfer.endpoint;

import com.juan.bank.mod.transfer.model.TransactionState;
import com.juan.bank.mod.transfer.model.TransactionStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Juan Mendoza 21/2/2023
 */

@RestController
@RequestMapping("/transaction-states")
public class TransactionStateController {

  @Autowired
  private TransactionStateService transactionStateService;

  @GetMapping
  public List<TransactionState> findAll(){
    return transactionStateService.findAll();
  }

  @PostMapping
  public ResponseEntity<TransactionState> create(@RequestBody TransactionState transactionState){
    TransactionState entity = new TransactionState();
    entity.setName(transactionState.getName());
    entity.setDescription(transactionState.getDescription());
    entity = transactionStateService.create(entity);

    return new ResponseEntity<>(entity, HttpStatus.OK);
  }
}
