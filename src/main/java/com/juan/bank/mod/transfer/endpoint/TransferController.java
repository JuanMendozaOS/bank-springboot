package com.juan.bank.mod.transfer.endpoint;

import com.juan.bank.mod.account.model.Account;
import com.juan.bank.mod.account.model.AccountService;
import com.juan.bank.mod.account.model.Movement;
import com.juan.bank.mod.account.model.MovementService;
import com.juan.bank.mod.balance.model.Balance;
import com.juan.bank.mod.balance.model.BalanceService;
import com.juan.bank.mod.bank.model.Bank;
import com.juan.bank.mod.bank.model.BankService;
import com.juan.bank.mod.currency.model.Currency;
import com.juan.bank.mod.currency.model.CurrencyService;
import com.juan.bank.mod.customer.model.CustomerService;
import com.juan.bank.mod.customer.model.DocumentType;
import com.juan.bank.mod.customer.model.DocumentTypeService;
import com.juan.bank.mod.transfer.model.TransactionStateService;
import com.juan.bank.mod.transfer.model.Transfer;
import com.juan.bank.mod.transfer.model.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Juan Mendoza 20/2/2023
 */
@RestController
@RequestMapping
public class TransferController {

  private final TransferService transferService;
  private final TransactionStateService transactionStateService;
  private final AccountService accountService;
  private final BalanceService balanceService;
  private final MovementService movementService;
  private final BankService bankService;
  private final CurrencyService currencyService;
  private final DocumentTypeService documentTypeService;
  private final CustomerService customerService;
  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  public TransferController(TransferService transferService, TransactionStateService transactionStateService, AccountService accountService, BalanceService balanceService, MovementService movementService, BankService bankService, CurrencyService currencyService, DocumentTypeService documentTypeService, CustomerService customerService) {
    this.transferService = transferService;
    this.transactionStateService = transactionStateService;
    this.accountService = accountService;
    this.balanceService = balanceService;
    this.movementService = movementService;
    this.bankService = bankService;
    this.currencyService = currencyService;
    this.documentTypeService = documentTypeService;
    this.customerService = customerService;
  }


  @GetMapping("/transfers")
  public List<Transfer> findAll() {
    return transferService.findAll();
  }

  @GetMapping("/transfers/{transferId}")
  public Transfer get(@PathVariable("transferId") Long transferId) {
    return transferService.findById(transferId);
  }


  @PostMapping(value = "/transfers", consumes = {"application/json"}, produces = "application/json")
  public ResponseEntity<?> create(@RequestBody Transfer transfer) {

    Bank myBank = bankService.findByCode("BJABCDEXXX");
    if (containsNullOrEmpty(transfer)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Se pasó al menos un dato vacío");
    }

    Currency currency = currencyService.findByIsoCode(transfer.getCurrencyIsoCode());
    Bank fromBank = bankService.findByCode(transfer.getFromBankCode());
    Bank toBank = bankService.findByCode(transfer.getToBankCode());

    if (currency == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Se debe pasar la moneda");
    }
    if (transfer.getAmount().compareTo(BigDecimal.ZERO) < 1) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El monto debe ser positivo");
    }

    if (!fromBank.equals(myBank) && !toBank.equals(myBank)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ambos bancos son externos");
    }
    if (fromBank.equals(myBank) && toBank.equals(myBank)) {
      return internalTransfer(transfer);

    } else if (fromBank.equals(myBank)) {
      return sendTransfer(transfer);

    } else {
      return receiveTransfer(transfer);
    }
  }

  private ResponseEntity<Transfer> receiveTransfer(Transfer transfer) {
    try {
      Account toAccount = accountService.findByIban(transfer.getToIban());
      if (toAccount == null) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      if (!toAccount.getCustomer().getDocumentNumber().equals(transfer.getToDocumentNumber())) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

      Balance toBalance = balanceService.findByAccountId(toAccount.getId());

      // acá debo crear el transfer
      Transfer entity = new Transfer();
      entity.setTransactionState("EN PROCESO");
      entity = createTransfer(transfer);

      // aca debo afectar mi balance y crear un nuevo movimiento
      updateBalance(toBalance, entity.getAmount());
      createMovement(entity, toAccount, entity.getAmount(), false);

      entity.setAccreditationDate(LocalDateTime.now());
      entity.setTransactionState("FINALIZADO");
      entity.setExternalTransferId(transfer.getId());
      entity = transferService.update(entity);
      return new ResponseEntity<>(entity, HttpStatus.OK);

    } catch (Exception e) {
      e.printStackTrace();
      Transfer entity = transfer;
      entity.setTransactionState("RECHAZADO");
      transferService.update(entity);
      return new ResponseEntity<>(entity, HttpStatus.CONFLICT);
    }
  }

  private ResponseEntity<Transfer> sendTransfer(Transfer transfer) {
    Account fromAccount = accountService.findByIban(transfer.getFromIban());
    if (fromAccount == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    if (!fromAccount.getCustomer().getDocumentNumber().equals(transfer.getFromDocumentNumber())) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Balance fromBalance = balanceService.findByAccountId(fromAccount.getId());
    BigDecimal amountBalance = fromBalance.getAmount();

    if (amountBalance.compareTo(transfer.getAmount()) < 1) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    // acá debo crear el transfer
    Transfer entity = new Transfer();
    entity.setTransactionState("EN PROCESO");
    entity = createTransfer(transfer);
    try {

      // aca debo afectar mi balance y crear un nuevo movimiento
      updateBalance(fromBalance, entity.getAmount().negate());
      createMovement(entity, fromAccount, entity.getAmount().negate(), false);

      // enviar solicitud y esperar una respuesta
      ResponseEntity<Transfer> response = postTransferRequest(entity, "http://192.168.126.49:8080/transfers");

      // si response OK
      if (response.getStatusCode().equals(HttpStatus.OK)) {
        // guardar en un campo el id del otro y guardar en el transfer
        entity = response.getBody();
        entity.setTransactionState("FINALIZADO");
        entity.setAccreditationDate(LocalDateTime.now());
        // intercambio de id's para tener el externalId correcto
        Long aux;
        aux = entity.getId();
        entity.setId(entity.getExternalTransferId());
        entity.setExternalTransferId(aux);
        
        entity = transferService.update(entity);
        return new ResponseEntity<>(entity, HttpStatus.OK);

      } else { // si no el response not ok
        // revertir la afectación del balance y crear un movimiento al revertir el cambio en el balance
        updateBalance(fromBalance, entity.getAmount());
        createMovement(entity, fromAccount, entity.getAmount(), true);
        // actualizar el transfer como rechazado
        entity.setTransactionState("RECHAZADO");
        // retornar not okay
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

    } catch (Exception e) {
      updateBalance(fromBalance, entity.getAmount());
      createMovement(entity, fromAccount, entity.getAmount(), true);
      // actualizar el transfer como rechazado
      entity.setTransactionState("RECHAZADO");
      transferService.update(entity);
      // retornar not okay
      return new ResponseEntity<>(entity, HttpStatus.BAD_REQUEST);
    }
  }


  private ResponseEntity<Transfer> internalTransfer(Transfer transfer) {
    Account fromAccount = accountService.findByIban(transfer.getFromIban());
    Account toAccount = accountService.findByIban(transfer.getToIban());

    if (!validateAccounts(transfer, fromAccount, toAccount)) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Balance fromBalance = balanceService.findByAccountId(fromAccount.getId());
    Balance toBalance = balanceService.findByAccountId(toAccount.getId());

    if (fromBalance.getAmount().compareTo(transfer.getAmount()) < 1) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // acá debo crear el transfer
    Transfer entity = transfer;
    entity.setTransactionState("EN PROCESO");
    entity = createTransfer(entity);

    // para cuenta de origen
    updateBalance(fromBalance, transfer.getAmount().negate());
    createMovement(transfer, fromAccount, transfer.getAmount().negate(), false);

    // para cuenta de destino
    updateBalance(toBalance, transfer.getAmount());
    createMovement(transfer, toAccount, transfer.getAmount(), false);

    entity.setAccreditationDate(LocalDateTime.now());
    entity.setTransactionState("FINALIZADO");
    entity = transferService.update(entity);
    return new ResponseEntity<>(entity, HttpStatus.OK);
  }


  private void updateBalance(Balance balance, BigDecimal transferAmount) {
    BigDecimal balanceAmount = balance.getAmount();
    balance.setAmount(balanceAmount.add(transferAmount));
    balance.setLastModified(LocalDateTime.now());
    balanceService.update(balance);
  }

  private boolean containsNullOrEmpty(Transfer transfer) {
    if (transfer == null || transfer.getFromIban() == null || transfer.getFromDocumentNumber() == null
            || transfer.getFromBankCode() == null || transfer.getFromDocumentTypeName() == null
            || transfer.getToIban() == null || transfer.getToDocumentNumber() == null
            || transfer.getToBankCode() == null || transfer.getToDocumentTypeName() == null 
            || transfer.getAmount() == null || transfer.getCurrencyIsoCode() == null) {
      return true;
    }
    if (transfer.getFromIban().isEmpty() || transfer.getFromDocumentNumber().isEmpty()
            || transfer.getFromBankCode().isEmpty() || transfer.getToIban().isEmpty()
            || transfer.getToDocumentNumber().isEmpty() || transfer.getToBankCode().isEmpty()
            || transfer.getToDocumentTypeName().isEmpty() || transfer.getFromDocumentTypeName().isEmpty() 
            || transfer.getCurrencyIsoCode().isEmpty()) {
      return true;
    }
    return false;
  }

  private void createMovement(Transfer transfer, Account account, BigDecimal transferAmount, boolean isReverseTransfer) {
    try {
      Movement movement = new Movement();
      Balance balance = balanceService.findByAccountId(account.getId());

      movement.setAccount(account);
      movement.setDateTime(LocalDateTime.now());
      movement.setAmount(transferAmount);
      movement.setBalance(balance);
      movement.setTransfer(transfer);
      movement.setCurrencyIsoCode(transfer.getCurrencyIsoCode());
      movement.setReverseTransfer(isReverseTransfer);
      movementService.create(movement);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private Transfer createTransfer(Transfer transfer) {
    Transfer entity = transfer;
    DocumentType fromDocType = documentTypeService.findByName(transfer.getFromDocumentTypeName());
    DocumentType toDocType = documentTypeService.findByName(transfer.getToDocumentTypeName());

    entity.setFromDocumentType(fromDocType);
    entity.setToDocumentType(toDocType);
    entity.setOperationDate(LocalDateTime.now());
    return transferService.create(entity);
  }


  private ResponseEntity<Transfer> postTransferRequest(Transfer transfer, String url) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<Transfer> requestEntity = new HttpEntity<>(transfer, headers);

    ResponseEntity<Transfer> response = restTemplate.exchange(url,
            HttpMethod.POST, requestEntity, Transfer.class);
    return response;
  }

  private boolean validateAccounts(Transfer transfer, Account fromAccount, Account toAccount) {
 
    if (fromAccount == null) {
      System.out.println("Cuenta de origen incorrecta");
      return false;
    }
    if (!fromAccount.getCustomer().getDocumentNumber().equals(transfer.getFromDocumentNumber())) {
      System.out.println("Número de documento de origen incorrecto");
      return false;
    }
    if (!fromAccount.getCustomer().getDocumentTypeName().equals(transfer.getFromDocumentTypeName())) {
      System.out.println("Tipo de documento de origen incorrecto");
      return false;
    }
    if (toAccount == null) {
      System.out.println("Cuenta de destino incorrecta");
      return false;
    }
    if (!toAccount.getCustomer().getDocumentNumber().equals(transfer.getToDocumentNumber())) {
      System.out.println("Número de documento de destino incorrecto");
      return false;

    }
    if (!toAccount.getCustomer().getDocumentTypeName().equals(transfer.getToDocumentTypeName())) {
      System.out.println("Tipo de documento de destino incorrecto");
      return false;
    }
    if (!toAccount.isEnabled()) {
      System.out.println("Cuenta de destino no habilitada");
      return false;
    }
    return true;
  }
}
