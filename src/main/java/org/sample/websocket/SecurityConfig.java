package org.sample.websocket;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
        .antMatchers("/css/**", "/js/**", "/img/**", "**/favicon.ico").anonymous()
        .antMatchers("/notify/**").permitAll()
        .antMatchers("/userssessions**").permitAll()
        .anyRequest().hasRole("USER")
        .and()
      .formLogin()
        .permitAll()
    ;
  }
   
  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .inMemoryAuthentication()
        .withUser("mike").password("password").roles("USER").and()
        .withUser("jim").password("password").roles("USER");
  }
 
}