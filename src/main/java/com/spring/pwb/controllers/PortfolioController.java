package com.spring.pwb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/portfolio"})
public class PortfolioController {
  @GetMapping
  public String displayPortfolioPage() {
    return "portfolio/portfolio";
  }
}
