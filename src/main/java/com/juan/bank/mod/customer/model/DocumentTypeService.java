package com.juan.bank.mod.customer.model;

import com.juan.bank.app.model.EntityServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Juan Mendoza 16/2/2023
 */
@Service
public class DocumentTypeService implements EntityServiceInterface<DocumentType> {

  @Autowired
  private DocumentTypeRepository documentTypeRepo;


  @Override
  public DocumentType create(DocumentType entity) {
    return documentTypeRepo.save(entity);
  }

  @Override
  public DocumentType update(DocumentType entity) {
    return documentTypeRepo.save(entity);
  }

  @Override
  public DocumentType findById(Long id) {
    Optional<DocumentType> documentTypeOptional;
    documentTypeOptional = documentTypeRepo.findById(id);
    return documentTypeOptional.orElse(null);
  }

  @Override
  public List<DocumentType> findAll() {
    return documentTypeRepo.findAll();
  }

  public DocumentType findByName(String documentTypeName) {
    return documentTypeRepo.findByName(documentTypeName);
  }
}
