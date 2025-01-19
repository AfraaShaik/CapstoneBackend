package com.bankingSystem.config;

import com.bankingSystem.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationConfiguration {
    private final UserRepository userRepository;

    public ApplicationConfiguration(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    /*
    The userDetailsService() defines how to retrieve the user using the UserRepository that is injected.
     */
    @Bean
    UserDetailsService userDetailsService(){
        return username-> userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
    }

    //The passwordEncoder() creates an instance of the BCryptPasswordEncoder() used to encode the plain user password.
    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    // The authenticationProvider() sets the new strategy to perform the authentication.
    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
