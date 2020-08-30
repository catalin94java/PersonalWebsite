package com.spring.pwb.validators;

import com.spring.pwb.annotation.ValidPassword;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.Rule;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
  public void initialize(ValidPassword arg0) {}
  
  public boolean isValid(String password, ConstraintValidatorContext context) {
    PasswordValidator validator = new PasswordValidator(Arrays.asList(new Rule[] { (Rule)new LengthRule(8, 80), (Rule)new CharacterRule((CharacterData)EnglishCharacterData.UpperCase, 1), (Rule)new CharacterRule((CharacterData)EnglishCharacterData.LowerCase, 1), (Rule)new CharacterRule((CharacterData)EnglishCharacterData.Digit, 1), (Rule)new CharacterRule((CharacterData)EnglishCharacterData.Special, 1), (Rule)new WhitespaceRule() }));
    RuleResult result = validator.validate(new PasswordData(password));
    if (result.isValid())
      return true; 
    List<String> messages = validator.getMessages(result);
    String messageTemplate = messages.stream().collect(Collectors.joining(","));
    context.buildConstraintViolationWithTemplate(messageTemplate)
      .addConstraintViolation()
      .disableDefaultConstraintViolation();
    return false;
  }
}
