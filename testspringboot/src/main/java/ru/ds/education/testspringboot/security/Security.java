package ru.ds.education.testspringboot.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .usersByUsernameQuery("select name, password, active from users where name=?")
                .authoritiesByUsernameQuery("select u.name, r.name from users u inner join users_roles ur on u.id = ur.user_id join roles r on r.id = ur.role_id where u.name=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/resources/**").permitAll()
                    .antMatchers("/").permitAll()
                    .antMatchers("/admin").hasRole("ADMIN")
                    .antMatchers("/main").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/buy").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/cart").hasAnyRole("USER", "ADMIN")
                    .antMatchers(HttpMethod.POST, "/api/users").hasAnyRole("USER", "ADMIN")
                    .antMatchers(HttpMethod.POST, "/api/users/update").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/users/get/**").hasAnyRole("ADMIN", "USER")
                    .antMatchers("/api/tovar/get/**").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/tovar/get").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/carts/**").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/remind/**").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/category/get").hasAnyRole("ADMIN", "USER")
                    .antMatchers(HttpMethod.POST, "/api/category").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/api/tovar").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PUT, "/api/tovar/update").hasRole("ADMIN")
//                    .anyRequest()
//                    .authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .defaultSuccessUrl("/success", true)
                    .failureForwardUrl("/failure")
                .and()
                    .logout((logout) -> logout.permitAll());
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}