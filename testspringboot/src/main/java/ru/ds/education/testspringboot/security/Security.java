package ru.ds.education.testspringboot.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class Security{

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername("User")
                .password(passwordEncoder().encode("User"))
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("Admin")
                .password(passwordEncoder().encode("Admin"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/users").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/users/update").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/users/get/**").hasRole("ADMIN")
                .antMatchers("/api/tovar/get/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/tovar/get").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/carts/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/remind/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/category/get").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/api/category").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/tovar").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/tovar/update").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}