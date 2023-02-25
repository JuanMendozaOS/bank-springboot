package com.juan.bank.mod.customer.endpoint;

import com.juan.bank.mod.customer.model.DocumentType;
import com.juan.bank.mod.customer.model.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Juan Mendoza 16/2/2023
 */
@RestController
@RequestMapping("/documents")
public class DocumentTypeController {

  @Autowired
  private DocumentTypeService documentTypeService;

  @GetMapping
  public List<DocumentType> findAll(){
    return documentTypeService.findAll();
  }

  @PostMapping
  public ResponseEntity<DocumentType> addDocumentType(@RequestBody DocumentType documentType) {
    DocumentType entity = new DocumentType();
    entity.setName(documentType.getName());
    entity.setDescription(documentType.getDescription());
    entity = documentTypeService.create(entity);
    return new ResponseEntity<>(entity, HttpStatus.OK);
  }
}
