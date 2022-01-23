package com.beers.rest.security.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //TODO - Add Configuration to define actions according roles - By now all requests all are allowed
        http.csrf().disable();
        http.authorizeRequests().anyRequest().permitAll();
    }

}
