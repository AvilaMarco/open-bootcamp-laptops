package com.example.ejercicio101112.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain( HttpSecurity http) throws Exception {
        http.csrf().disable()
          .authorizeHttpRequests((authz) -> authz
            .requestMatchers(DELETE, "/api/laptops").hasRole("ADMIN")
            .requestMatchers("/").anonymous()
            .anyRequest().authenticated()
          )
          .formLogin(withDefaults())
          .httpBasic(withDefaults())
          ;

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.builder()
          .username("user")
          .password("123")
          .roles("USER")
          .passwordEncoder(encoder()::encode)
          .build();

        UserDetails admin = User.builder()
          .username("admin")
          .password("ñ")
          .roles("USER", "ADMIN")
          .passwordEncoder(encoder()::encode)
          .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}
