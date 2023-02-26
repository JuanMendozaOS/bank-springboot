package com.juan.bank.mod.user.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.juan.bank.app.model.EntityServiceInterface;

/**
 * @author Juan Mendoza 11/2/2023
 */
@Service
public class UserService implements EntityServiceInterface<User> {

  @Autowired
  private UserRepository userRepo;

  @Override
  public User create(User user) {
    return userRepo.save(user);
  }

  @Override
  public User update(User user) {
    return userRepo.save(user);
  }

  @Override
  public User findById(Long id) {
    Optional<User> userOptional;
    userOptional = userRepo.findById(id);
    return userOptional.orElse(null);
  }

  @Override
  public List<User> findAll() {
    return userRepo.findAll();
  }

  public boolean existsByUsername(String username) {
    return userRepo.existsByUsername(username);
  }

  public boolean existsByEmail(String email) {
    return userRepo.existsByEmail(email);
  }

  public User findByUsername(String username) {
    return userRepo.findByUsername(username);
  }

  public User findByEmail(String email) {
    return userRepo.findByEmail(email);
  }
}
