package com.juan.bank.mod.user.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Juan Mendoza
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
}
