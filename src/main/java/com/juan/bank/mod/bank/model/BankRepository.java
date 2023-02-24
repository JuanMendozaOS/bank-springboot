package com.juan.bank.mod.bank.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Mendoza
 */
@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

  boolean existsByNameIgnoreCase(String name);

  Bank findByNameIgnoreCase(String name);

  boolean existsByCode(String bankCode);

  Bank findByCode(String fromBankCode);

//  @Query("SELECT a FROM Bank a WHERE a.name = :name")
//  List<Bank> esteQueryNoSirve(String name);

}
