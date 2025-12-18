package com.example.demo.security;

import static org.hibernate.internal.HEMLogging.logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

// this file deals with the auth during the login here as initailly i had some deoprecated stuff 
// there was error we need change those 

@SuppressWarnings("deprecation")
//@Slf4j
@Configuration
public class AuthProviderConfig {

    @Bean
    @SuppressWarnings("deprecation")
    public AuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        //logger.debug("AuthenticationProvider configured with UserDetailsService and PasswordEncoder");
        return authProvider;
    }
}
