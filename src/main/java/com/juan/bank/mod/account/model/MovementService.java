package com.juan.bank.mod.account.model;

import com.juan.bank.app.model.EntityServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Juan Mendoza 21/2/2023
 */
@Service
public class MovementService implements EntityServiceInterface<Movement> {

  @Autowired
  private MovementRepository movementRepo;

  @Override
  public Movement create(Movement entity) {
    return movementRepo.save(entity);
  }

  @Override
  public Movement update(Movement entity) {
    return null;
  }

  @Override
  public Movement findById(Long id) {
    Optional<Movement> movementOptional;
    movementOptional = movementRepo.findById(id);
    return movementOptional.orElse(null);
  }

  @Override
  public List<Movement> findAll() {
    return movementRepo.findAll();
  }

  public List<Movement> findAllAccountMovements(Long id){
    return movementRepo.findAllMovementsByAccountId(id);
  }
}
