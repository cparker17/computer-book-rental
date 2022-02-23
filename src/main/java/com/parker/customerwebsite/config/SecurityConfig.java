package com.parker.customerwebsite.config;

import com.parker.customerwebsite.services.SecurityUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    SecurityUserService securityUserService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()

                //ApplicationController end points
                .mvcMatchers(HttpMethod.GET, "/", "/webjars/**", "/css/**",
                        "/login/**", "/images/**", "/register").permitAll()
                .mvcMatchers("/customer-list").hasRole("ADMIN")

                //SecurityUserController end points
                .mvcMatchers(HttpMethod.POST, "/register", "/register-form", "/sign-in",
                        "/login-error").permitAll()

                //CustomerController end points
                .mvcMatchers("/edit/{id}", "/update/{id}").authenticated()
                .mvcMatchers("/delete{id}", "/customer-list", "/checkout-book/{id}",
                        "/return-book/{id}").hasRole("ADMIN")

                //BookController end points
                .mvcMatchers("/books", "/books/available").authenticated()
                .mvcMatchers("/books/new", "/books/save", "/books/update/{id}",
                        "/books/delete/{id}", "/books/edit/{id}", "/books/checkout/{bookId}/{customerId}",
                        "/books/return/{bookId}/{customerId}").hasRole("ADMIN")

                .and()
                .formLogin()
                    .loginPage("/login").permitAll()
                    .failureUrl("/login-error").permitAll()
                .and()
                .logout()
                    .logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(securityUserService)
                .passwordEncoder(passwordEncoder());
    }
}

