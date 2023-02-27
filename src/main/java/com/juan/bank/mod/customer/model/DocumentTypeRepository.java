package com.juan.bank.mod.customer.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Mendoza 16/2/2023
 */
@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long> {
  DocumentType findByName(String documentTypeName);
}
