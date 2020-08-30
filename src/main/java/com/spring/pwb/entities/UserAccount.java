package com.spring.pwb.entities;

import com.spring.pwb.annotation.ValidPassword;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "user_accounts")
public class UserAccount {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_accounts_seq")
  @SequenceGenerator(name = "user_accounts_seq", sequenceName = "user_accounts_seq", allocationSize = 1)
  @Column(name = "user_id")
  private long userId;
  
  @Column(name = "username")
  @NotEmpty
  private String userName;
  
  @Column(name = "email")
  @NotEmpty
  @Email
  private String email;
  
  @ValidPassword
  private String password;
  
  @Transient
  private String confirmPassword;
  
  private String role = "USER";
  
  private boolean enabled = false;
  
  public String getRole() {
    return this.role;
  }
  
  public void setRole(String role) {
    this.role = role;
  }
  
  public long getUserId() {
    return this.userId;
  }
  
  public void setUserId(long userId) {
    this.userId = userId;
  }
  
  public String getUserName() {
    return this.userName;
  }
  
  public void setUserName(String userName) {
    this.userName = userName;
  }
  
  public String getEmail() {
    return this.email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getPassword() {
    return this.password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public String getConfirmPassword() {
    return this.confirmPassword;
  }
  
  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }
  
  public boolean isEnabled() {
    return this.enabled;
  }
  
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
}
