package com.juan.bank.mod.account.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Mendoza
 * 16/2/2023
 */

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
}
