package com.spring.pwb.entities;

import com.spring.pwb.entities.UserAccount;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "confirmation_token")
public class ConfirmationToken {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "confirmation_token_seq")
  @SequenceGenerator(name = "confirmation_token_seq", sequenceName = "confirmation_token_seq", allocationSize = 1)
  @Column(name = "token_id")
  private long tokenid;
  
  @Column(name = "confirmation_token")
  private String confirmationToken;
  
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDate;
  
  @OneToOne(targetEntity = UserAccount.class, fetch = FetchType.EAGER)
  @JoinColumn(nullable = false, name = "user_id")
  private UserAccount userAccount;
  
  public ConfirmationToken() {}
  
  public ConfirmationToken(UserAccount userAccount) {
    this.userAccount = userAccount;
    this.createdDate = new Date();
    this.confirmationToken = UUID.randomUUID().toString();
  }
  
  public long getTokenid() {
    return this.tokenid;
  }
  
  public void setTokenid(long tokenid) {
    this.tokenid = tokenid;
  }
  
  public String getConfirmationToken() {
    return this.confirmationToken;
  }
  
  public void setConfirmationToken(String confirmationToken) {
    this.confirmationToken = confirmationToken;
  }
  
  public Date getCreatedDate() {
    return this.createdDate;
  }
  
  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
  
  public UserAccount getUserAccount() {
    return this.userAccount;
  }
  
  public void setUserAccount(UserAccount userAccount) {
    this.userAccount = userAccount;
  }
}
