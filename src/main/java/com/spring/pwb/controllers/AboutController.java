package com.spring.pwb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/about"})
public class AboutController {
  @GetMapping
  public String displayAboutPage() {
    return "about/about";
  }
}
