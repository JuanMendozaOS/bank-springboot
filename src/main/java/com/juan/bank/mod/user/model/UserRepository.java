package com.juan.bank.mod.user.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Juan Mendoza
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  User findByUsername(String username);

  User findByEmail(String email);
}
