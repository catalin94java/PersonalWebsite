package com.spring.pwb.security;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Autowired
  DataSource dataSource;
  
  @Autowired
  BCryptPasswordEncoder bCryptEncoder;
  
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    String userByMailQuery = "SELECT email, password, enabled FROM user_accounts WHERE email = ?;";
    String userByUsernameQuery = "SELECT email, password, enabled FROM user_accounts WHERE username= ?";
    String roleByMailQuery = "SELECT email, role FROM user_accounts WHERE email = ?;";
    ((JdbcUserDetailsManagerConfigurer)auth.jdbcAuthentication().dataSource(this.dataSource).passwordEncoder((PasswordEncoder)this.bCryptEncoder))
      .usersByUsernameQuery(userByMailQuery)
      .authoritiesByUsernameQuery(roleByMailQuery);
    ((JdbcUserDetailsManagerConfigurer)auth.jdbcAuthentication().dataSource(this.dataSource).passwordEncoder((PasswordEncoder)this.bCryptEncoder))
      .usersByUsernameQuery(userByUsernameQuery)
      .authoritiesByUsernameQuery(roleByMailQuery);
  }
  
  protected void configure(HttpSecurity http) throws Exception {
    ((HttpSecurity)((FormLoginConfigurer)((FormLoginConfigurer)((HttpSecurity)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)http.authorizeRequests()
      
      .antMatchers(new String[] { "/" })).permitAll()
      .and())
      .formLogin().loginPage("/login").failureUrl("/login")).defaultSuccessUrl("/", true))
      .and()).logout().logoutRequestMatcher((RequestMatcher)new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
  }
}
