package com.parker.computerbookrental.config;

import com.parker.computerbookrental.services.SecurityUserService;
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
                .mvcMatchers("/user-list").hasRole("ADMIN")

                //SecurityUserController end points
                .mvcMatchers(HttpMethod.POST, "/register", "/register-form", "/sign-in",
                        "/login-error").permitAll()

                //UserController end points
                .mvcMatchers( "/update", "/checkout-book/{id}",
                        "/return-book/{id}", "/dashboard").authenticated()
                .mvcMatchers("/delete{id}", "/user-list").hasRole("ADMIN")

                //BookController end points
                .mvcMatchers( "/books/checkout/{bookId}",
                        "/books/return/{bookId}/{userId}").authenticated()
                .mvcMatchers("/books/new", "/books/save", "/books/update/{id}",
                        "/books/delete/{id}", "/books/edit/{id}").hasRole("ADMIN")
                .mvcMatchers("/books", "/books/available", "/books/cover-image/{bookId}",
                        "books/new-arrivals", "/books/search").permitAll()

                //AdminController
                .mvcMatchers( "/delete/{id}", "/user-list", "/user-books/{id}",
                        "/user/rental-history/{id}", "/books/rental-history/{id}").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST, "/update/{id}").hasRole("ADMIN")

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
        auth.userDetailsService(securityUserService)
                .passwordEncoder(passwordEncoder());
    }
}

