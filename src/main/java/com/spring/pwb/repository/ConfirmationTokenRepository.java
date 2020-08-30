package com.spring.pwb.repository;

import com.spring.pwb.entities.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Long> {
  ConfirmationToken findByConfirmationToken(String paramString);
}
