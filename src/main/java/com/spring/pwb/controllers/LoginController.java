package com.spring.pwb.controllers;

import com.spring.pwb.entities.Login;
import com.spring.pwb.repository.ConfirmationTokenRepository;
import com.spring.pwb.repository.UserAccountRepository;
import com.spring.pwb.services.EmailSenderService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
  @Autowired
  UserAccountRepository accountRepo;
  
  @Autowired
  BCryptPasswordEncoder bCryptEncoder;
  
  @Autowired
  ConfirmationTokenRepository confTokenRepo;
  
  @Autowired
  EmailSenderService emailSenderService;
  
  @GetMapping({"/login"})
  public String displayLoginForm(HttpServletRequest request, Model model) {
    Login login = new Login();
    model.addAttribute("login", login);
    HttpSession session = request.getSession(false);
    String errorMessage = null;
    if (session != null) {
      AuthenticationException ex = (AuthenticationException)session
        .getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
      if (ex != null) {
        errorMessage = "There was a problem logging in. Check your username/email and password or create an account.";
        model.addAttribute("errorMessage", errorMessage);
      } 
    } 
    return "login/login";
  }
  
  @PostMapping({"/login"})
  public String afterLogin() {
    return "redirect:/";
  }
}
