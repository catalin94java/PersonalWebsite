package com.spring.pwb.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppErrorController implements ErrorController {
  @GetMapping({"/error"})
  public String handleError(HttpServletRequest request) {
    Object status = request.getAttribute("javax.servlet.error.status_code");
    if (status != null) {
      Integer statusCode = Integer.valueOf(status.toString());
      if (statusCode.intValue() == HttpStatus.NOT_FOUND.value())
        return "errorpages/error-404"; 
      if (statusCode.intValue() == HttpStatus.INTERNAL_SERVER_ERROR.value())
        return "errorpages/error-500"; 
      if (statusCode.intValue() == HttpStatus.FORBIDDEN.value())
        return "errorpages/error-403"; 
    } 
    return "errorpages/error";
  }
  
  public String getErrorPath() {
    return "/error";
  }
}
