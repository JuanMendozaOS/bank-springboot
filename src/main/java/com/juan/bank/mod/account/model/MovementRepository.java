package com.juan.bank.mod.account.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Juan Mendoza 21/2/2023
 */
@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {
  List<Movement> findAllMovementsByAccountId(Long id);
}
