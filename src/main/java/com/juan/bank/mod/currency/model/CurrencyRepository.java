package com.juan.bank.mod.currency.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Mendoza 15/2/2023
 */
@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

  boolean existsByIsoCode(String isoCode);

  Currency findByIsoCode(String currencyIsoCode);

  boolean existsByName(String name);
}
