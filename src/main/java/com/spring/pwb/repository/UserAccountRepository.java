package com.spring.pwb.repository;

import com.spring.pwb.entities.UserAccount;
import org.springframework.data.repository.CrudRepository;

public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {
  UserAccount findByUserName(String paramString);
  
  UserAccount findByEmail(String paramString);
  
  UserAccount findByEmailIgnoreCase(String paramString);
}
