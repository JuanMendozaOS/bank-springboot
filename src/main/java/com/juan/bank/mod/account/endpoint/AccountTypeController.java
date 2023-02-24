package com.juan.bank.mod.account.endpoint;

import com.juan.bank.mod.account.model.AccountType;
import com.juan.bank.mod.account.model.AccountTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Juan Mendoza
 * 16/2/2023
 */
@RestController
@RequestMapping("/accounts/types")
public class AccountTypeController {

  @Autowired
  private AccountTypeService accTypeService;

  @GetMapping
  public List<AccountType> findAll(){
    return accTypeService.findAll();
  }

  @PostMapping
  public ResponseEntity<AccountType> addAccountType(@RequestBody AccountType accountType){
    AccountType entity = new AccountType();
    entity.setName(accountType.getName());
    entity.setDescription(accountType.getDescription());
    entity = accTypeService.create(entity);
    return new ResponseEntity<>(entity, HttpStatus.OK);
  }
}
