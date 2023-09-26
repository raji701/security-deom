package com.imaginnovate.websecuritycheck.config;

import com.imaginnovate.websecuritycheck.filter.JwtAuthFilter;
import com.imaginnovate.websecuritycheck.service.AppUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;

@Configuration
@EnableWebSecurity
public class WebConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    //authentication
    @Bean
    public UserDetailsService userDetailsService (){
        return new AppUserServiceImpl();
    }

    //authentication provider
    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(encoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }
    //authorization bean
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception{
      return   http.csrf().disable()
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers("/new-user","/authenticate").permitAll();
                    auth.requestMatchers("/users").hasAnyRole("ADMIN");
                })
              .sessionManagement()
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
              .and()
              .authenticationProvider(authenticationProvider())
              .addFilterBefore( jwtAuthFilter , UsernamePasswordAuthenticationFilter.class).build();
    }

    //password encoder
    @Bean
    public PasswordEncoder encoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

        return config.getAuthenticationManager();
    }
}
