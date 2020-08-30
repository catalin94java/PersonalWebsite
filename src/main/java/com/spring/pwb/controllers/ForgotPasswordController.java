package com.spring.pwb.controllers;

import com.spring.pwb.entities.ConfirmationToken;
import com.spring.pwb.entities.UserAccount;
import com.spring.pwb.repository.ConfirmationTokenRepository;
import com.spring.pwb.repository.UserAccountRepository;
import com.spring.pwb.services.EmailSenderService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ForgotPasswordController {
  @Autowired
  UserAccountRepository accountRepo;
  
  @Autowired
  BCryptPasswordEncoder bCryptEncoder;
  
  @Autowired
  ConfirmationTokenRepository confTokenRepo;
  
  @Autowired
  EmailSenderService emailSenderService;
  
  @GetMapping({"/forgot-password"})
  public String displayForgotPasswordForm(Model model, UserAccount userAccount) {
    return "forgotpassword/forgot-password";
  }
  
  @PostMapping({"/forgot-password"})
  public String sendForgotPasswordToken(Model model, UserAccount userAccount, BindingResult bindingResult) {
    UserAccount existingUser = this.accountRepo.findByEmailIgnoreCase(userAccount.getEmail());
    String errorMessage = null;
    if (existingUser != null) {
      ConfirmationToken confirmationToken = new ConfirmationToken(existingUser);
      this.confTokenRepo.save(confirmationToken);
      SimpleMailMessage mailMessage = new SimpleMailMessage();
      mailMessage.setTo(existingUser.getEmail());
      mailMessage.setSubject("Complete Password Reset!");
      mailMessage.setFrom("it@codewillbe.com");
      mailMessage.setText("To complete the password reset process, please click here: https://codewillbe.com/confirm-reset?token=" + 
          confirmationToken.getConfirmationToken());
      this.emailSenderService.sendEmail(mailMessage);
    } else {
      errorMessage = "This email address does not exists";
      model.addAttribute("errorMessage", errorMessage);
      return "forgotpassword/forgot-password";
    } 
    String successMessage = "We succesfully sent you a reset-password link to " + userAccount.getEmail() + ", please check your inbox";
    model.addAttribute(userAccount);
    model.addAttribute("successmessage", successMessage);
    return "forgotpassword/forgot-password-activation";
  }
  
  @RequestMapping(value = {"/confirm-reset"}, method = {RequestMethod.GET, RequestMethod.POST})
  public String validateResetToken(Model model, @RequestParam("token") String confirmationToken) {
    ConfirmationToken token = this.confTokenRepo.findByConfirmationToken(confirmationToken);
    if (token != null) {
      UserAccount user = this.accountRepo.findByEmailIgnoreCase(token.getUserAccount().getEmail());
      user.setEnabled(true);
      this.accountRepo.save(user);
      model.addAttribute("userAccount", user);
      model.addAttribute("email", user.getEmail());
      model.addAttribute("userName", user.getUserName());
    } else {
      model.addAttribute("errorMessage", "The activation link is invalid or has expired.");
      return "errorpages/error-token";
    } 
    return "forgotpassword/reset-password";
  }
  
  @PostMapping({"/reset-password"})
  public String resetUserPassword(@Valid Model model, @Valid UserAccount userAccount, BindingResult bindingResult, RedirectAttributes redirAttributes) {
    if (userAccount.getEmail() != null) {
      UserAccount tokenUser = this.accountRepo.findByEmailIgnoreCase(userAccount.getEmail());
      model.addAttribute("userName", tokenUser.getUserName());
      if (!userAccount.getPassword().equals(userAccount.getConfirmPassword())) {
        bindingResult.rejectValue("password", "error.UserAccount", "The password does not match");
        return "forgotpassword/reset-password";
      } 
      tokenUser.setPassword(this.bCryptEncoder.encode(userAccount.getPassword()));
      this.accountRepo.save(tokenUser);
      String successMessage = "Password successfully reset for " + userAccount.getEmail() + ". You can now log in with the new credentials.";
      redirAttributes.addFlashAttribute("successmessage", successMessage);
    } else {
      model.addAttribute("errorMessage", "The activation link is invalid or has expired. ");
      return "errorpages/error-token";
    } 
    if (bindingResult.hasErrors()) {
      System.out.println(bindingResult.getAllErrors());
      return "forgotpassword/reset-password";
    } 
    return "redirect:/login";
  }
}
