package com.example.simplework.config;

import com.example.simplework.filter.JwtAuthenticationFilter;
import com.example.simplework.filter.JwtAuthorizationFilter;
import com.example.simplework.service.JwtService;
import com.example.simplework.utils.JwtUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    private final JwtUtil jwtUtil;

    public SecurityConfig(UserDetailsService userDetailsService, JwtService jwtService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/api/register")
                .permitAll().anyRequest().authenticated().and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtUtil))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userDetailsService, jwtService));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
