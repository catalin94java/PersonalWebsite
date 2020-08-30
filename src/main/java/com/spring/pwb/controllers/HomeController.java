package com.spring.pwb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
  @GetMapping
  public String Home() {
    return "main/home";
  }
  
  @PostMapping({"/portfolio"})
  public String seeProjectsFromComputer(@RequestParam(value = "y", required = false) String y) {
    String success = "y";
    if (!y.equalsIgnoreCase(success))
      return "main/home"; 
    return "portfolio/portfolio";
  }
}
