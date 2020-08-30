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
public class RegisterController {
  @Autowired
  UserAccountRepository accountRepo;
  
  @Autowired
  BCryptPasswordEncoder bCryptEncoder;
  
  @Autowired
  ConfirmationTokenRepository confTokenRepo;
  
  @Autowired
  EmailSenderService emailSenderService;
  
  @GetMapping({"/register"})
  public String displayRegister(Model model) {
    UserAccount userAccount = new UserAccount();
    model.addAttribute("userAccount", userAccount);
    return "register/register";
  }
  
  @PostMapping({"/register"})
  public String saveUserAccount(@Valid Model model, @Valid UserAccount userAccount, BindingResult bindingResult) {
    UserAccount userNameExists = this.accountRepo.findByUserName(userAccount.getUserName().toLowerCase());
    if (userNameExists != null) {
      bindingResult.rejectValue("userName", "error.UserAccount", "There is already an account registered with this username");
      return "register/register";
    } 
    UserAccount emailExists = this.accountRepo.findByEmail(userAccount.getEmail());
    if (emailExists != null) {
      bindingResult.rejectValue("email", "error.UserAccount", "There is already an account registered with this email");
      return "register/register";
    } 
    if (!userAccount.getPassword().equals(userAccount.getConfirmPassword())) {
      bindingResult.rejectValue("password", "error.UserAccount", "The password does not match");
      return "register/register";
    } 
    if (bindingResult.hasErrors())
      return "register/register"; 
    userAccount.setPassword(this.bCryptEncoder.encode(userAccount.getPassword()));
    this.accountRepo.save(userAccount);
    ConfirmationToken confirmationToken = new ConfirmationToken(userAccount);
    this.confTokenRepo.save(confirmationToken);
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setTo(userAccount.getEmail());
    mailMessage.setSubject("Complete Registration!");
    mailMessage.setFrom("it@codewillbe.com");
    mailMessage.setText("To confirm your account created on codewillbe.com, please click here: https://codewillbe.com/confirm-account?token=" + confirmationToken.getConfirmationToken());
    this.emailSenderService.sendEmail(mailMessage);
    model.addAttribute("email", userAccount.getEmail());
    String successMessage = "Your account was successfully created. We have sent you an activation link at " + userAccount.getEmail() + ". Please check your inbox and click the activation link for your account.";
    model.addAttribute(userAccount);
    model.addAttribute("successmessage", successMessage);
    return "register/register-activation-link";
  }
  
  @RequestMapping(value = {"/confirm-account"}, method = {RequestMethod.GET, RequestMethod.POST})
  public String confirmUserAccount(Model model, @RequestParam("token") String confirmationToken, RedirectAttributes redirAttributes) {
    ConfirmationToken token = this.confTokenRepo.findByConfirmationToken(confirmationToken);
    if (token != null) {
      UserAccount user = this.accountRepo.findByEmailIgnoreCase(token.getUserAccount().getEmail());
      user.setEnabled(true);
      this.accountRepo.save(user);
    } else {
      model.addAttribute("errorMessage", "The activation link is invalid or has expired.");
      return "errorpages/error-token";
    } 
    String successMessage = "Your account was successfully Activated. You can login now.";
    redirAttributes.addFlashAttribute("successmessage", successMessage);
    return "redirect:https://codewillbe.com/login";
  }
}
